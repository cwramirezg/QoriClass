@file:OptIn(ExperimentalMaterial3Api::class)

package com.cwramirezg.home.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Data classes para cursos
data class AreaCurricular(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val color: Long, // Color en formato ARGB
    val competencias: List<Competencia> = emptyList(),
    val sincronizado: Boolean = false
)

data class Competencia(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val capacidades: List<String> = emptyList()
)

@Composable
fun CourseScreen(
    onNavigateBack: () -> Unit = {}
) {
    var showAddAreaDialog by remember { mutableStateOf(false) }
    var showAddCompetenciaDialog by remember { mutableStateOf(false) }
    var selectedArea by remember { mutableStateOf<AreaCurricular?>(null) }
    var selectedCompetencia by remember { mutableStateOf<Competencia?>(null) }
    var expandedAreaId by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    // Datos simulados de áreas curriculares según CNEB
    val areasCurriculares = remember {
        mutableStateListOf(
            AreaCurricular(
                id = "1",
                nombre = "Matemática",
                descripcion = "Desarrolla el pensamiento matemático y la resolución de problemas",
                color = 0xFF2196F3,
                competencias = listOf(
                    Competencia(
                        id = "1-1",
                        nombre = "Resuelve problemas de cantidad",
                        descripcion = "Traduce cantidades a expresiones numéricas",
                        capacidades = listOf(
                            "Traduce cantidades a expresiones numéricas",
                            "Comunica su comprensión sobre números",
                            "Usa estrategias y procedimientos",
                            "Argumenta afirmaciones sobre relaciones numéricas"
                        )
                    ),
                    Competencia(
                        id = "1-2",
                        nombre = "Resuelve problemas de regularidad, equivalencia y cambio",
                        descripcion = "Traduce datos a expresiones algebraicas",
                        capacidades = listOf(
                            "Traduce datos a expresiones algebraicas",
                            "Comunica su comprensión sobre relaciones",
                            "Usa estrategias y procedimientos",
                            "Argumenta afirmaciones sobre cambio"
                        )
                    )
                ),
                sincronizado = true
            ),
            AreaCurricular(
                id = "2",
                nombre = "Comunicación",
                descripcion = "Desarrolla competencias comunicativas y literarias",
                color = 0xFFE91E63,
                competencias = listOf(
                    Competencia(
                        id = "2-1",
                        nombre = "Lee diversos tipos de textos",
                        descripcion = "Obtiene información del texto escrito",
                        capacidades = listOf(
                            "Obtiene información del texto",
                            "Infiere e interpreta información",
                            "Reflexiona y evalúa"
                        )
                    ),
                    Competencia(
                        id = "2-2",
                        nombre = "Escribe diversos tipos de textos",
                        descripcion = "Adecúa el texto a la situación comunicativa",
                        capacidades = listOf(
                            "Adecúa el texto",
                            "Organiza y desarrolla ideas",
                            "Utiliza convenciones del lenguaje",
                            "Reflexiona y evalúa"
                        )
                    )
                ),
                sincronizado = true
            ),
            AreaCurricular(
                id = "3",
                nombre = "Ciencia y Tecnología",
                descripcion = "Desarrolla indagación científica y competencias tecnológicas",
                color = 0xFF4CAF50,
                competencias = listOf(
                    Competencia(
                        id = "3-1",
                        nombre = "Indaga mediante métodos científicos",
                        descripcion = "Problematiza situaciones para hacer indagación",
                        capacidades = listOf(
                            "Problematiza situaciones",
                            "Diseña estrategias de indagación",
                            "Genera y registra datos",
                            "Analiza datos e información",
                            "Evalúa y comunica"
                        )
                    )
                ),
                sincronizado = false
            ),
            AreaCurricular(
                id = "4",
                nombre = "Personal Social",
                descripcion = "Desarrolla competencias ciudadanas e históricas",
                color = 0xFFFF9800,
                competencias = listOf(
                    Competencia(
                        id = "4-1",
                        nombre = "Construye su identidad",
                        descripcion = "Se valora a sí mismo",
                        capacidades = listOf(
                            "Se valora a sí mismo",
                            "Autorregula sus emociones",
                            "Reflexiona y argumenta éticamente",
                            "Vive su sexualidad de manera integral"
                        )
                    )
                ),
                sincronizado = true
            )
        )
    }

    // Filtrado de áreas
    val areasFiltradas = remember(searchQuery, areasCurriculares.toList()) {
        if (searchQuery.isEmpty()) {
            areasCurriculares
        } else {
            areasCurriculares.filter { area ->
                area.nombre.contains(searchQuery, ignoreCase = true) ||
                        area.competencias.any { it.nombre.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gestión de Cursos",
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
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedArea = null
                    showAddAreaDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar área curricular"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar área o competencia") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Contador
            Text(
                text = "${areasFiltradas.size} área${if (areasFiltradas.size != 1) "s" else ""} curricular${if (areasFiltradas.size != 1) "es" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Lista de áreas curriculares
            if (areasFiltradas.isEmpty()) {
                EmptyStateView()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(areasFiltradas, key = { it.id }) { area ->
                        AreaCurricularCard(
                            area = area,
                            isExpanded = expandedAreaId == area.id,
                            onExpandClick = {
                                expandedAreaId = if (expandedAreaId == area.id) null else area.id
                            },
                            onEditArea = {
                                selectedArea = area
                                showAddAreaDialog = true
                            },
                            onDeleteArea = {
                                areasCurriculares.remove(area)
                            },
                            onAddCompetencia = {
                                selectedArea = area
                                selectedCompetencia = null
                                showAddCompetenciaDialog = true
                            },
                            onEditCompetencia = { competencia ->
                                selectedArea = area
                                selectedCompetencia = competencia
                                showAddCompetenciaDialog = true
                            },
                            onDeleteCompetencia = { competencia ->
                                val index = areasCurriculares.indexOfFirst { it.id == area.id }
                                if (index != -1) {
                                    val updatedArea = area.copy(
                                        competencias = area.competencias.filter { it.id != competencia.id }
                                    )
                                    areasCurriculares[index] = updatedArea
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Dialog para agregar/editar área curricular
    if (showAddAreaDialog) {
        AgregarEditarAreaDialog(
            area = selectedArea,
            onDismiss = {
                showAddAreaDialog = false
                selectedArea = null
            },
            onConfirm = { area ->
                if (selectedArea != null) {
                    // Editar
                    val index = areasCurriculares.indexOfFirst { it.id == area.id }
                    if (index != -1) {
                        areasCurriculares[index] =
                            area.copy(competencias = areasCurriculares[index].competencias)
                    }
                } else {
                    // Agregar nueva
                    areasCurriculares.add(area)
                }
                showAddAreaDialog = false
                selectedArea = null
            }
        )
    }

    // Dialog para agregar/editar competencia
    if (showAddCompetenciaDialog && selectedArea != null) {
        AgregarEditarCompetenciaDialog(
            competencia = selectedCompetencia,
            onDismiss = {
                showAddCompetenciaDialog = false
                selectedCompetencia = null
                selectedArea = null
            },
            onConfirm = { competencia ->
                val areaIndex = areasCurriculares.indexOfFirst { it.id == selectedArea!!.id }
                if (areaIndex != -1) {
                    val area = areasCurriculares[areaIndex]
                    val updatedCompetencias = if (selectedCompetencia != null) {
                        // Editar competencia existente
                        area.competencias.map { if (it.id == competencia.id) competencia else it }
                    } else {
                        // Agregar nueva competencia
                        area.competencias + competencia
                    }
                    areasCurriculares[areaIndex] = area.copy(competencias = updatedCompetencias)
                }
                showAddCompetenciaDialog = false
                selectedCompetencia = null
                selectedArea = null
            }
        )
    }
}

@Composable
private fun AreaCurricularCard(
    area: AreaCurricular,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onEditArea: () -> Unit,
    onDeleteArea: () -> Unit,
    onAddCompetencia: () -> Unit,
    onEditCompetencia: (Competencia) -> Unit,
    onDeleteCompetencia: (Competencia) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

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
            // Header del área
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onExpandClick)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Indicador de color
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(area.color))
                ) {
                    Icon(
                        imageVector = Icons.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = area.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${area.competencias.size} competencia${if (area.competencias.size != 1) "s" else ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Indicador de sincronización
                Icon(
                    imageVector = if (area.sincronizado) Icons.Filled.CloudDone else Icons.Filled.CloudOff,
                    contentDescription = if (area.sincronizado) "Sincronizado" else "Pendiente",
                    tint = if (area.sincronizado)
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
                            text = { Text("Editar área") },
                            onClick = {
                                showMenu = false
                                onEditArea()
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Eliminar área") },
                            onClick = {
                                showMenu = false
                                onDeleteArea()
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

            // Contenido expandible - Competencias
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

                    // Descripción del área
                    Text(
                        text = area.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Competencias",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        TextButton(onClick = onAddCompetencia) {
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

                    if (area.competencias.isEmpty()) {
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
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No hay competencias registradas",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            area.competencias.forEach { competencia ->
                                CompetenciaCard(
                                    competencia = competencia,
                                    areaColor = Color(area.color),
                                    onEdit = { onEditCompetencia(competencia) },
                                    onDelete = { onDeleteCompetencia(competencia) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompetenciaCard(
    competencia: Competencia,
    areaColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showCapacidades by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCapacidades = !showCapacidades }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(areaColor)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = competencia.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (competencia.capacidades.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${competencia.capacidades.size} capacidad${if (competencia.capacidades.size != 1) "es" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = { showCapacidades = !showCapacidades }) {
                    Icon(
                        imageVector = if (showCapacidades) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (showCapacidades) "Contraer" else "Expandir",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Más opciones",
                            modifier = Modifier.size(20.dp)
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

            // Capacidades expandibles
            AnimatedVisibility(
                visible = showCapacidades && competencia.capacidades.isNotEmpty(),
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 12.dp, bottom = 12.dp)
                ) {
                    Text(
                        text = "Capacidades:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    competencia.capacidades.forEach { capacidad ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "• ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = capacidad,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.LibraryBooks,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No hay áreas curriculares",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Agrega áreas curriculares y sus competencias",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun AgregarEditarAreaDialog(
    area: AreaCurricular?,
    onDismiss: () -> Unit,
    onConfirm: (AreaCurricular) -> Unit
) {
    var nombre by remember { mutableStateOf(area?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(area?.descripcion ?: "") }
    var colorSeleccionado by remember { mutableStateOf(area?.color ?: 0xFF2196F3) }

    val coloresDisponibles = remember {
        listOf(
            0xFF2196F3, // Azul
            0xFFE91E63, // Rosa
            0xFF4CAF50, // Verde
            0xFFFF9800, // Naranja
            0xFF9C27B0, // Morado
            0xFFF44336, // Rojo
            0xFF00BCD4, // Cian
            0xFFFFEB3B  // Amarillo
        )
    }

    val focusManager = LocalFocusManager.current
    val isValid = nombre.isNotBlank() && descripcion.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (area != null) "Editar Área Curricular" else "Agregar Área Curricular",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del área") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )

                Text(
                    text = "Color identificador",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    coloresDisponibles.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(color))
                                .clickable { colorSeleccionado = color }
                                .then(
                                    if (colorSeleccionado == color) {
                                        Modifier.border(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (colorSeleccionado == color) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(
                        AreaCurricular(
                            id = area?.id ?: System.currentTimeMillis().toString(),
                            nombre = nombre,
                            descripcion = descripcion,
                            color = colorSeleccionado,
                            competencias = area?.competencias ?: emptyList(),
                            sincronizado = false
                        )
                    )
                },
                enabled = isValid
            ) {
                Text(if (area != null) "Guardar" else "Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun AgregarEditarCompetenciaDialog(
    competencia: Competencia?,
    onDismiss: () -> Unit,
    onConfirm: (Competencia) -> Unit
) {
    var nombre by remember { mutableStateOf(competencia?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(competencia?.descripcion ?: "") }
    var capacidades by remember { mutableStateOf(competencia?.capacidades ?: emptyList()) }
    var nuevaCapacidad by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val isValid = nombre.isNotBlank() && descripcion.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (competencia != null) "Editar Competencia" else "Agregar Competencia",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre de la competencia") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        )
                    )
                }

                item {
                    HorizontalDivider()
                }

                item {
                    Text(
                        text = "Capacidades",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        OutlinedTextField(
                            value = nuevaCapacidad,
                            onValueChange = { nuevaCapacidad = it },
                            label = { Text("Nueva capacidad") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (nuevaCapacidad.isNotBlank()) {
                                        capacidades = capacidades + nuevaCapacidad.trim()
                                        nuevaCapacidad = ""
                                    }
                                }
                            )
                        )

                        IconButton(
                            onClick = {
                                if (nuevaCapacidad.isNotBlank()) {
                                    capacidades = capacidades + nuevaCapacidad.trim()
                                    nuevaCapacidad = ""
                                }
                            },
                            enabled = nuevaCapacidad.isNotBlank()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Agregar capacidad"
                            )
                        }
                    }
                }

                items(capacidades.size) { index ->
                    val capacidad = capacidades[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = capacidad,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = {
                                    capacidades = capacidades.filterIndexed { i, _ -> i != index }
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar capacidad",
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(
                        Competencia(
                            id = competencia?.id ?: System.currentTimeMillis().toString(),
                            nombre = nombre,
                            descripcion = descripcion,
                            capacidades = capacidades
                        )
                    )
                },
                enabled = isValid
            ) {
                Text(if (competencia != null) "Guardar" else "Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}