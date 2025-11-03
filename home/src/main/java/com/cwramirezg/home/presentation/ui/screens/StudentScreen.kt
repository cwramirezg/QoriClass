@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.UploadFile
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data class para el estudiante (simulación)
data class Estudiante(
    val id: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val codigoSiagie: String,
    val grado: String,
    val seccion: String,
    val sincronizado: Boolean = false
)

@Composable
fun StudentScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToImportarExcel: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedGrado by remember { mutableStateOf<String?>(null) }
    var selectedSeccion by remember { mutableStateOf<String?>(null) }
    var selectedEstudiante by remember { mutableStateOf<Estudiante?>(null) }

    // Lista simulada de estudiantes
    val estudiantesSimulados = remember {
        mutableStateListOf(
            Estudiante("1", "Juan Carlos", "García", "López", "00123456", "1", "A", true),
            Estudiante("2", "María Elena", "Rodríguez", "Martínez", "00123457", "1", "A", true),
            Estudiante("3", "Pedro José", "Fernández", "Sánchez", "00123458", "1", "B", false),
            Estudiante("4", "Ana Lucía", "Torres", "Ramírez", "00123459", "2", "A", true),
            Estudiante("5", "Carlos Alberto", "Vega", "Flores", "00123460", "2", "B", false)
        )
    }

    // Filtrado de estudiantes
    val estudiantesFiltrados =
        remember(searchQuery, selectedGrado, selectedSeccion, estudiantesSimulados.toList()) {
            estudiantesSimulados.filter { estudiante ->
                val matchesSearch = searchQuery.isEmpty() ||
                        estudiante.nombres.contains(searchQuery, ignoreCase = true) ||
                        estudiante.apellidoPaterno.contains(searchQuery, ignoreCase = true) ||
                        estudiante.apellidoMaterno.contains(searchQuery, ignoreCase = true) ||
                        estudiante.codigoSiagie.contains(searchQuery, ignoreCase = true)

                val matchesGrado = selectedGrado == null || estudiante.grado == selectedGrado
                val matchesSeccion =
                    selectedSeccion == null || estudiante.seccion == selectedSeccion

                matchesSearch && matchesGrado && matchesSeccion
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gestión de Estudiantes",
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
                            containerColor = if (selectedGrado != null || selectedSeccion != null)
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
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // FAB secundario - Importar Excel
                SmallFloatingActionButton(
                    onClick = onNavigateToImportarExcel,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Filled.UploadFile,
                        contentDescription = "Importar Excel"
                    )
                }

                // FAB principal - Agregar estudiante
                FloatingActionButton(
                    onClick = { showAddDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Agregar estudiante"
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de búsqueda
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onClearClick = { searchQuery = "" },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Chips de filtros activos
            AnimatedVisibility(
                visible = selectedGrado != null || selectedSeccion != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedGrado?.let { grado ->
                        FilterChip(
                            selected = true,
                            onClick = { selectedGrado = null },
                            label = { Text("Grado $grado") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Quitar filtro",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }

                    selectedSeccion?.let { seccion ->
                        FilterChip(
                            selected = true,
                            onClick = { selectedSeccion = null },
                            label = { Text("Sección $seccion") },
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

            // Contador de estudiantes
            Text(
                text = "${estudiantesFiltrados.size} estudiante${if (estudiantesFiltrados.size != 1) "s" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Lista de estudiantes
            if (estudiantesFiltrados.isEmpty()) {
                EmptyStateView(
                    hasActiveFilters = searchQuery.isNotEmpty() || selectedGrado != null || selectedSeccion != null
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estudiantesFiltrados, key = { it.id }) { estudiante ->
                        EstudianteCard(
                            estudiante = estudiante,
                            onClick = { selectedEstudiante = estudiante },
                            onEdit = {
                                selectedEstudiante = estudiante
                                showAddDialog = true
                            },
                            onDelete = { estudiantesSimulados.remove(estudiante) }
                        )
                    }
                }
            }
        }
    }

    // Dialog para agregar/editar estudiante
    if (showAddDialog) {
        AgregarEditarEstudianteDialog(
            estudiante = selectedEstudiante,
            onDismiss = {
                showAddDialog = false
                selectedEstudiante = null
            },
            onConfirm = { estudiante ->
                if (selectedEstudiante != null) {
                    // Editar
                    val index = estudiantesSimulados.indexOfFirst { it.id == estudiante.id }
                    if (index != -1) {
                        estudiantesSimulados[index] = estudiante
                    }
                } else {
                    // Agregar nuevo
                    estudiantesSimulados.add(estudiante)
                }
                showAddDialog = false
                selectedEstudiante = null
            }
        )
    }

    // Bottom Sheet para filtros
    if (showFilterSheet) {
        FilterBottomSheet(
            selectedGrado = selectedGrado,
            selectedSeccion = selectedSeccion,
            onGradoSelected = { selectedGrado = it },
            onSeccionSelected = { selectedSeccion = it },
            onDismiss = { showFilterSheet = false },
            onClearFilters = {
                selectedGrado = null
                selectedSeccion = null
                showFilterSheet = false
            }
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Buscar por nombre o código SIAGIE") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
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
}

@Composable
private fun EstudianteCard(
    estudiante: Estudiante,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${estudiante.nombres.first()}${estudiante.apellidoPaterno.first()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${estudiante.apellidoPaterno} ${estudiante.apellidoMaterno}, ${estudiante.nombres}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Código: ${estudiante.codigoSiagie}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${estudiante.grado}° ${estudiante.seccion}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Indicador de sincronización
            Icon(
                imageVector = if (estudiante.sincronizado) Icons.Filled.CloudDone else Icons.Filled.CloudOff,
                contentDescription = if (estudiante.sincronizado) "Sincronizado" else "Pendiente",
                tint = if (estudiante.sincronizado)
                    MaterialTheme.colorScheme.tertiary
                else
                    MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Menú de acciones
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
private fun EmptyStateView(
    hasActiveFilters: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (hasActiveFilters) Icons.Filled.SearchOff else Icons.Filled.PersonOff,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasActiveFilters) "No se encontraron estudiantes" else "No hay estudiantes registrados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (hasActiveFilters)
                "Intenta con otros filtros de búsqueda"
            else
                "Agrega estudiantes o importa desde Excel",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun AgregarEditarEstudianteDialog(
    estudiante: Estudiante?,
    onDismiss: () -> Unit,
    onConfirm: (Estudiante) -> Unit
) {
    var nombres by remember { mutableStateOf(estudiante?.nombres ?: "") }
    var apellidoPaterno by remember { mutableStateOf(estudiante?.apellidoPaterno ?: "") }
    var apellidoMaterno by remember { mutableStateOf(estudiante?.apellidoMaterno ?: "") }
    var codigoSiagie by remember { mutableStateOf(estudiante?.codigoSiagie ?: "") }
    var grado by remember { mutableStateOf(estudiante?.grado ?: "1") }
    var seccion by remember { mutableStateOf(estudiante?.seccion ?: "A") }

    val focusManager = LocalFocusManager.current
    val isValid = nombres.isNotBlank() && apellidoPaterno.isNotBlank() &&
            apellidoMaterno.isNotBlank() && codigoSiagie.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (estudiante != null) "Editar Estudiante" else "Agregar Estudiante",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = { Text("Nombres") },
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
                    value = apellidoPaterno,
                    onValueChange = { apellidoPaterno = it },
                    label = { Text("Apellido Paterno") },
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
                    value = apellidoMaterno,
                    onValueChange = { apellidoMaterno = it },
                    label = { Text("Apellido Materno") },
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
                    value = codigoSiagie,
                    onValueChange = { if (it.length <= 8) codigoSiagie = it },
                    label = { Text("Código SIAGIE") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    supportingText = { Text("8 dígitos") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    var expandedGrado by remember { mutableStateOf(false) }
                    var expandedSeccion by remember { mutableStateOf(false) }

                    // Selector de Grado
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
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(
                        Estudiante(
                            id = estudiante?.id ?: System.currentTimeMillis().toString(),
                            nombres = nombres,
                            apellidoPaterno = apellidoPaterno,
                            apellidoMaterno = apellidoMaterno,
                            codigoSiagie = codigoSiagie,
                            grado = grado,
                            seccion = seccion,
                            sincronizado = false
                        )
                    )
                },
                enabled = isValid
            ) {
                Text(if (estudiante != null) "Guardar" else "Agregar")
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
private fun FilterBottomSheet(
    selectedGrado: String?,
    selectedSeccion: String?,
    onGradoSelected: (String?) -> Unit,
    onSeccionSelected: (String?) -> Unit,
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
                text = "Grado",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..6).forEach { grado ->
                    FilterChip(
                        selected = selectedGrado == grado.toString(),
                        onClick = {
                            onGradoSelected(if (selectedGrado == grado.toString()) null else grado.toString())
                        },
                        label = { Text("$grado°") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sección",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ('A'..'F').forEach { seccion ->
                    FilterChip(
                        selected = selectedSeccion == seccion.toString(),
                        onClick = {
                            onSeccionSelected(if (selectedSeccion == seccion.toString()) null else seccion.toString())
                        },
                        label = { Text(seccion.toString()) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun StudentScreenPreview() {
    StudentScreen()
}