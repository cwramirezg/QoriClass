package com.cwramirezg.classroom.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cwramirezg.classroom.domain.model.Aula
import com.cwramirezg.classroom.domain.model.DiaSemana
import com.cwramirezg.classroom.domain.model.Horario
import com.cwramirezg.classroom.domain.model.NivelEducativo
import com.cwramirezg.classroom.domain.model.Turno

@Composable
fun AulaCard(
    aula: Aula,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onEditAula: () -> Unit,
    onDeleteAula: () -> Unit,
    onAddHorario: () -> Unit,
    onEditHorario: (Horario) -> Unit,
    onDeleteHorario: (Horario) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    val ocupacionPercentage = if (aula.capacidad > 0) {
        (aula.totalEstudiantes.toFloat() / aula.capacidad) * 100
    } else 0f

    val ocupacionColor = when {
        ocupacionPercentage >= 90 -> MaterialTheme.colorScheme.error
        ocupacionPercentage >= 75 -> Color(0xFFFF9800) // Naranja
        else -> MaterialTheme.colorScheme.tertiary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header del aula
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onExpandClick)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge de grado y sección
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${aula.grado}°",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = aula.seccion,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${aula.nivel.displayName} - ${aula.grado}° ${aula.seccion}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Turno ${aula.turno.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = ocupacionColor
                        )
                        Text(
                            text = "${aula.totalEstudiantes}/${aula.capacidad} estudiantes",
                            style = MaterialTheme.typography.bodySmall,
                            color = ocupacionColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Indicador de sincronización
                Icon(
                    imageVector = if (aula.sincronizado) Icons.Filled.CloudDone else Icons.Filled.CloudOff,
                    contentDescription = if (aula.sincronizado) "Sincronizado" else "Pendiente",
                    tint = if (aula.sincronizado)
                        MaterialTheme.colorScheme.tertiary
                    else
                        MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Botón expandir/contraer
                IconButton(onClick = onExpandClick) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Contraer" else "Expandir"
                    )
                }

                // Menú de opciones
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar aula") },
                            onClick = {
                                showMenu = false
                                onEditAula()
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Eliminar aula") },
                            onClick = {
                                showMenu = false
                                onDeleteAula()
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }
            }

            // Contenido expandible - Información adicional y horarios
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del tutor
                    if (aula.docenteTutor != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Tutor(a)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = aula.docenteTutor,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Horarios
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Horarios (${aula.horarios.size})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        TextButton(onClick = onAddHorario) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Agregar")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (aula.horarios.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarToday,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No hay horarios registrados",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        // Agrupar horarios por día
                        val horariosPorDia = aula.horarios.groupBy { it.diaSemana }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DiaSemana.values().forEach { dia ->
                                val horariosDelDia = horariosPorDia[dia] ?: emptyList()
                                if (horariosDelDia.isNotEmpty()) {
                                    DiaHorarioSection(
                                        dia = dia,
                                        horarios = horariosDelDia,
                                        onEditHorario = onEditHorario,
                                        onDeleteHorario = onDeleteHorario
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AulaCardPreview() {
    AulaCard(
        aula = Aula(
            id = "1",
            grado = "1",
            seccion = "A",
            nivel = NivelEducativo.PRIMARIA,
            turno = Turno.MANANA,
            capacidad = 30,
            totalEstudiantes = 28,
            docenteTutor = "Prof. María García",
            horarios = listOf(
                Horario(
                    "h1",
                    DiaSemana.LUNES,
                    "08:00",
                    "09:30",
                    "Matemática",
                    "Prof. Juan Pérez"
                ),
                Horario(
                    "h2",
                    DiaSemana.LUNES,
                    "09:30",
                    "11:00",
                    "Comunicación",
                    "Prof. Ana Torres"
                ),
                Horario(
                    "h3",
                    DiaSemana.MARTES,
                    "08:00",
                    "09:30",
                    "Ciencia y Tecnología",
                    "Prof. Carlos Ruiz"
                )
            ),
            sincronizado = true
        ),
        isExpanded = false,
        onExpandClick = {},
        onEditAula = {},
        onDeleteAula = {},
        onAddHorario = {},
        onEditHorario = {},
        onDeleteHorario = {}
    )
}
