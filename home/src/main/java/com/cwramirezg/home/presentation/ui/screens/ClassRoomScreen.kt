package com.cwramirezg.home.presentation.ui.screens

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Data classes para aulas
data class Aula(
    val id: String,
    val grado: String,
    val seccion: String,
    val nivel: NivelEducativo,
    val turno: Turno,
    val capacidad: Int,
    val totalEstudiantes: Int,
    val docenteTutor: String?,
    val horarios: List<Horario> = emptyList(),
    val sincronizado: Boolean = false
)

enum class NivelEducativo(val displayName: String) {
    INICIAL("Inicial"),
    PRIMARIA("Primaria"),
    SECUNDARIA("Secundaria")
}

enum class Turno(val displayName: String) {
    MANANA("Mañana"),
    TARDE("Tarde"),
    NOCHE("Noche")
}

data class Horario(
    val id: String,
    val diaSemana: DiaSemana,
    val horaInicio: String, // HH:mm
    val horaFin: String, // HH:mm
    val areaCurricular: String,
    val docente: String?
)

enum class DiaSemana(val displayName: String, val abreviatura: String) {
    LUNES("Lunes", "L"),
    MARTES("Martes", "M"),
    MIERCOLES("Miércoles", "X"),
    JUEVES("Jueves", "J"),
    VIERNES("Viernes", "V")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassRoomScreen(
    onNavigateBack: () -> Unit = {}
) {
    var showAddAulaDialog by remember { mutableStateOf(false) }
    var showAddHorarioDialog by remember { mutableStateOf(false) }
    var selectedAula by remember { mutableStateOf<Aula?>(null) }
    var selectedHorario by remember { mutableStateOf<Horario?>(null) }
    var expandedAulaId by remember { mutableStateOf<String?>(null) }
    var selectedNivelFilter by remember { mutableStateOf<NivelEducativo?>(null) }
    var selectedTurnoFilter by remember { mutableStateOf<Turno?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Datos simulados de aulas
    val aulas = remember {
        mutableStateListOf(
            Aula(
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
            Aula(
                id = "2",
                grado = "1",
                seccion = "B",
                nivel = NivelEducativo.PRIMARIA,
                turno = Turno.MANANA,
                capacidad = 30,
                totalEstudiantes = 25,
                docenteTutor = "Prof. Pedro Sánchez",
                horarios = emptyList(),
                sincronizado = false
            ),
            Aula(
                id = "3",
                grado = "2",
                seccion = "A",
                nivel = NivelEducativo.PRIMARIA,
                turno = Turno.TARDE,
                capacidad = 30,
                totalEstudiantes = 29,
                docenteTutor = "Prof. Laura Mendoza",
                horarios = listOf(
                    Horario(
                        "h4",
                        DiaSemana.LUNES,
                        "14:00",
                        "15:30",
                        "Matemática",
                        "Prof. Laura Mendoza"
                    ),
                    Horario(
                        "h5",
                        DiaSemana.MARTES,
                        "14:00",
                        "15:30",
                        "Personal Social",
                        "Prof. Roberto Lima"
                    )
                ),
                sincronizado = true
            ),
            Aula(
                id = "4",
                grado = "3",
                seccion = "A",
                nivel = NivelEducativo.SECUNDARIA,
                turno = Turno.MANANA,
                capacidad = 35,
                totalEstudiantes = 32,
                docenteTutor = "Prof. Miguel Ángeles",
                horarios = emptyList(),
                sincronizado = true
            )
        )
    }

    // Filtrado de aulas
    val aulasFiltradas = remember(selectedNivelFilter, selectedTurnoFilter, aulas.toList()) {
        aulas
            .filter { aula ->
                val matchesNivel = selectedNivelFilter == null || aula.nivel == selectedNivelFilter
                val matchesTurno = selectedTurnoFilter == null || aula.turno == selectedTurnoFilter
                matchesNivel && matchesTurno
            }
            .sortedWith(compareBy({ it.grado.toIntOrNull() ?: 0 }, { it.seccion }))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gestión de Aulas",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterSheet = true }) {
                        Badge(
                            containerColor = if (selectedNivelFilter != null || selectedTurnoFilter != null)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = "Filtrar"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedAula = null
                    showAddAulaDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar aula"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Chips de filtros activos
            AnimatedVisibility(
                visible = selectedNivelFilter != null || selectedTurnoFilter != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedNivelFilter?.let { nivel ->
                        FilterChip(
                            selected = true,
                            onClick = { selectedNivelFilter = null },
                            label = { Text(nivel.displayName) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Quitar filtro",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }

                    selectedTurnoFilter?.let { turno ->
                        FilterChip(
                            selected = true,
                            onClick = { selectedTurnoFilter = null },
                            label = { Text("Turno ${turno.displayName}") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Quitar filtro",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }
            }

            // Resumen estadístico
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ResumenItem(
                        valor = "${aulasFiltradas.size}",
                        etiqueta = "Aulas",
                        icono = Icons.Filled.Class
                    )

                    VerticalDivider(
                        modifier = Modifier.height(40.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                    )

                    ResumenItem(
                        valor = "${aulasFiltradas.sumOf { it.totalEstudiantes }}",
                        etiqueta = "Estudiantes",
                        icono = Icons.Filled.People
                    )

                    VerticalDivider(
                        modifier = Modifier.height(40.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                    )

                    ResumenItem(
                        valor = "${aulasFiltradas.count { it.horarios.isNotEmpty() }}",
                        etiqueta = "Con horario",
                        icono = Icons.Filled.Schedule
                    )
                }
            }

            // Lista de aulas
            if (aulasFiltradas.isEmpty()) {
                EmptyStateView(hasActiveFilters = selectedNivelFilter != null || selectedTurnoFilter != null)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(aulasFiltradas, key = { it.id }) { aula ->
                        AulaCard(
                            aula = aula,
                            isExpanded = expandedAulaId == aula.id,
                            onExpandClick = {
                                expandedAulaId = if (expandedAulaId == aula.id) null else aula.id
                            },
                            onEditAula = {
                                selectedAula = aula
                                showAddAulaDialog = true
                            },
                            onDeleteAula = {
                                aulas.remove(aula)
                            },
                            onAddHorario = {
                                selectedAula = aula
                                selectedHorario = null
                                showAddHorarioDialog = true
                            },
                            onEditHorario = { horario ->
                                selectedAula = aula
                                selectedHorario = horario
                                showAddHorarioDialog = true
                            },
                            onDeleteHorario = { horario ->
                                val index = aulas.indexOfFirst { it.id == aula.id }
                                if (index != -1) {
                                    val updatedAula = aula.copy(
                                        horarios = aula.horarios.filter { it.id != horario.id }
                                    )
                                    aulas[index] = updatedAula
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Dialog para agregar/editar aula
    if (showAddAulaDialog) {
        AgregarEditarAulaDialog(
            aula = selectedAula,
            onDismiss = {
                showAddAulaDialog = false
                selectedAula = null
            },
            onConfirm = { aula ->
                if (selectedAula != null) {
                    // Editar
                    val index = aulas.indexOfFirst { it.id == aula.id }
                    if (index != -1) {
                        aulas[index] = aula.copy(horarios = aulas[index].horarios)
                    }
                } else {
                    // Agregar nueva
                    aulas.add(aula)
                }
                showAddAulaDialog = false
                selectedAula = null
            }
        )
    }

    // Dialog para agregar/editar horario
    if (showAddHorarioDialog && selectedAula != null) {
        AgregarEditarHorarioDialog(
            horario = selectedHorario,
            onDismiss = {
                showAddHorarioDialog = false
                selectedHorario = null
                selectedAula = null
            },
            onConfirm = { horario ->
                val aulaIndex = aulas.indexOfFirst { it.id == selectedAula!!.id }
                if (aulaIndex != -1) {
                    val aula = aulas[aulaIndex]
                    val updatedHorarios = if (selectedHorario != null) {
                        // Editar horario existente
                        aula.horarios.map { if (it.id == horario.id) horario else it }
                    } else {
                        // Agregar nuevo horario
                        aula.horarios + horario
                    }
                    aulas[aulaIndex] = aula.copy(horarios = updatedHorarios)
                }
                showAddHorarioDialog = false
                selectedHorario = null
                selectedAula = null
            }
        )
    }

    // Bottom Sheet para filtros
    if (showFilterSheet) {
        FilterAulasBottomSheet(
            selectedNivel = selectedNivelFilter,
            selectedTurno = selectedTurnoFilter,
            onNivelSelected = { selectedNivelFilter = it },
            onTurnoSelected = { selectedTurnoFilter = it },
            onDismiss = { showFilterSheet = false },
            onClearFilters = {
                selectedNivelFilter = null
                selectedTurnoFilter = null
                showFilterSheet = false
            }
        )
    }
}

@Composable
private fun ResumenItem(
    valor: String,
    etiqueta: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = valor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun AulaCard(
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

@Composable
private fun DiaHorarioSection(
    dia: DiaSemana,
    horarios: List<Horario>,
    onEditHorario: (Horario) -> Unit,
    onDeleteHorario: (Horario) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dia.abreviatura,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = dia.displayName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            horarios.sortedBy { it.horaInicio }.forEach { horario ->
                HorarioItem(
                    horario = horario,
                    onEdit = { onEditHorario(horario) },
                    onDelete = { onDeleteHorario(horario) }
                )
                if (horario != horarios.last()) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun HorarioItem(
    horario: Horario,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = horario.areaCurricular,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${horario.horaInicio} - ${horario.horaFin}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (horario.docente != null) {
                    Text(
                        text = horario.docente,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Más opciones",
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            showMenu = false
                            onEdit()
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Edit, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            showMenu = false
                            onDelete()
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
    }
}

@Composable
private fun EmptyStateView(hasActiveFilters: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (hasActiveFilters) Icons.Filled.SearchOff else Icons.Filled.MeetingRoom,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasActiveFilters) "No se encontraron aulas" else "No hay aulas registradas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (hasActiveFilters)
                "Intenta con otros filtros"
            else
                "Agrega aulas para comenzar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgregarEditarAulaDialog(
    aula: Aula?,
    onDismiss: () -> Unit,
    onConfirm: (Aula) -> Unit
) {
    var grado by remember { mutableStateOf(aula?.grado ?: "1") }
    var seccion by remember { mutableStateOf(aula?.seccion ?: "A") }
    var nivel by remember { mutableStateOf(aula?.nivel ?: NivelEducativo.PRIMARIA) }
    var turno by remember { mutableStateOf(aula?.turno ?: Turno.MANANA) }
    var capacidad by remember { mutableStateOf(aula?.capacidad?.toString() ?: "30") }
    var docenteTutor by remember { mutableStateOf(aula?.docenteTutor ?: "") }

    val isValid = grado.isNotBlank() && seccion.isNotBlank() && capacidad.toIntOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (aula != null) "Editar Aula" else "Agregar Aula",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Selector de Nivel Educativo
                var expandedNivel by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedNivel,
                    onExpandedChange = { expandedNivel = it }
                ) {
                    OutlinedTextField(
                        value = nivel.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Nivel Educativo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedNivel) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedNivel,
                        onDismissRequest = { expandedNivel = false }
                    ) {
                        NivelEducativo.values().forEach { n ->
                            DropdownMenuItem(
                                text = { Text(n.displayName) },
                                onClick = {
                                    nivel = n
                                    expandedNivel = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Selector de Grado
                    var expandedGrado by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedGrado,
                        onExpandedChange = { expandedGrado = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = grado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Grado") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedGrado) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedGrado,
                            onDismissRequest = { expandedGrado = false }
                        ) {
                            (1..6).forEach { g ->
                                DropdownMenuItem(
                                    text = { Text("$g°") },
                                    onClick = {
                                        grado = g.toString()
                                        expandedGrado = false
                                    }
                                )
                            }
                        }
                    }

                    // Selector de Sección
                    var expandedSeccion by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedSeccion,
                        onExpandedChange = { expandedSeccion = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = seccion,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Sección") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expandedSeccion
                                )
                            },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedSeccion,
                            onDismissRequest = { expandedSeccion = false }
                        ) {
                            ('A'..'F').forEach { s ->
                                DropdownMenuItem(
                                    text = { Text(s.toString()) },
                                    onClick = {
                                        seccion = s.toString()
                                        expandedSeccion = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Selector de Turno
                var expandedTurno by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedTurno,
                    onExpandedChange = { expandedTurno = it }
                ) {
                    OutlinedTextField(
                        value = turno.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Turno") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedTurno) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTurno,
                        onDismissRequest = { expandedTurno = false }
                    ) {
                        Turno.values().forEach { t ->
                            DropdownMenuItem(
                                text = { Text(t.displayName) },
                                onClick = {
                                    turno = t
                                    expandedTurno = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = capacidad,
                    onValueChange = { if (it.all { char -> char.isDigit() }) capacidad = it },
                    label = { Text("Capacidad") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("Número máximo de estudiantes") }
                )

                OutlinedTextField(
                    value = docenteTutor,
                    onValueChange = { docenteTutor = it },
                    label = { Text("Docente Tutor (Opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(
                        Aula(
                            id = aula?.id ?: System.currentTimeMillis().toString(),
                            grado = grado,
                            seccion = seccion,
                            nivel = nivel,
                            turno = turno,
                            capacidad = capacidad.toIntOrNull() ?: 30,
                            totalEstudiantes = aula?.totalEstudiantes ?: 0,
                            docenteTutor = docenteTutor.ifBlank { null },
                            horarios = aula?.horarios ?: emptyList(),
                            sincronizado = false
                        )
                    )
                },
                enabled = isValid
            ) {
                Text(if (aula != null) "Guardar" else "Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgregarEditarHorarioDialog(
    horario: Horario?,
    onDismiss: () -> Unit,
    onConfirm: (Horario) -> Unit
) {
    var diaSemana by remember { mutableStateOf(horario?.diaSemana ?: DiaSemana.LUNES) }
    var horaInicio by remember { mutableStateOf(horario?.horaInicio ?: "08:00") }
    var horaFin by remember { mutableStateOf(horario?.horaFin ?: "09:30") }
    var areaCurricular by remember { mutableStateOf(horario?.areaCurricular ?: "") }
    var docente by remember { mutableStateOf(horario?.docente ?: "") }

    val isValid = areaCurricular.isNotBlank() && horaInicio.isNotBlank() && horaFin.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (horario != null) "Editar Horario" else "Agregar Horario",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Selector de Día
                var expandedDia by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedDia,
                    onExpandedChange = { expandedDia = it }
                ) {
                    OutlinedTextField(
                        value = diaSemana.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Día de la semana") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedDia) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDia,
                        onDismissRequest = { expandedDia = false }
                    ) {
                        DiaSemana.values().forEach { dia ->
                            DropdownMenuItem(
                                text = { Text(dia.displayName) },
                                onClick = {
                                    diaSemana = dia
                                    expandedDia = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = horaInicio,
                        onValueChange = { horaInicio = it },
                        label = { Text("Hora inicio") },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("08:00") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = horaFin,
                        onValueChange = { horaFin = it },
                        label = { Text("Hora fin") },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("09:30") },
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = areaCurricular,
                    onValueChange = { areaCurricular = it },
                    label = { Text("Área Curricular") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )

                OutlinedTextField(
                    value = docente,
                    onValueChange = { docente = it },
                    label = { Text("Docente (Opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(
                        Horario(
                            id = horario?.id ?: System.currentTimeMillis().toString(),
                            diaSemana = diaSemana,
                            horaInicio = horaInicio,
                            horaFin = horaFin,
                            areaCurricular = areaCurricular,
                            docente = docente.ifBlank { null }
                        )
                    )
                },
                enabled = isValid
            ) {
                Text(if (horario != null) "Guardar" else "Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterAulasBottomSheet(
    selectedNivel: NivelEducativo?,
    selectedTurno: Turno?,
    onNivelSelected: (NivelEducativo?) -> Unit,
    onTurnoSelected: (Turno?) -> Unit,
    onDismiss: () -> Unit,
    onClearFilters: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtros",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = onClearFilters) {
                    Text("Limpiar todo")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nivel Educativo",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NivelEducativo.values().forEach { nivel ->
                    FilterChip(
                        selected = selectedNivel == nivel,
                        onClick = {
                            onNivelSelected(if (selectedNivel == nivel) null else nivel)
                        },
                        label = { Text(nivel.displayName) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Turno",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Turno.values().forEach { turno ->
                    FilterChip(
                        selected = selectedTurno == turno,
                        onClick = {
                            onTurnoSelected(if (selectedTurno == turno) null else turno)
                        },
                        label = { Text("Turno ${turno.displayName}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}