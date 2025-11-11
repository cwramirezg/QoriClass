@file:OptIn(ExperimentalMaterial3Api::class)

package com.cwramirezg.classroom.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cwramirezg.classroom.domain.model.Aula
import com.cwramirezg.classroom.domain.model.DiaSemana
import com.cwramirezg.classroom.domain.model.Horario
import com.cwramirezg.classroom.domain.model.NivelEducativo
import com.cwramirezg.classroom.domain.model.Turno
import com.cwramirezg.classroom.presentation.ui.components.AgregarEditarAulaDialog
import com.cwramirezg.classroom.presentation.ui.components.AgregarEditarHorarioDialog
import com.cwramirezg.classroom.presentation.ui.components.AulaCard
import com.cwramirezg.classroom.presentation.ui.components.EmptyStateView
import com.cwramirezg.classroom.presentation.ui.components.FilterAulasBottomSheet
import com.cwramirezg.classroom.presentation.ui.components.ResumenItem

@Composable
fun ClassRoomScreen(
    onNavigateBack: () -> Unit = {}
) {
    var showAddAulaDialog by remember { mutableStateOf(false) }
    var showAddHorarioDialog by remember { mutableStateOf(false) }
    var selectedAula by remember { mutableStateOf<Aula?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }

    var selectedHorario by remember { mutableStateOf<Horario?>(null) }
    var expandedAulaId by remember { mutableStateOf<String?>(null) }
    var selectedNivelFilter by remember { mutableStateOf<NivelEducativo?>(null) }
    var selectedTurnoFilter by remember { mutableStateOf<Turno?>(null) }

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
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
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
fun ClassRoomScreenContent() {

}
