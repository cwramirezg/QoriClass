@file:OptIn(ExperimentalMaterial3Api::class)

package com.cwramirezg.home.presentation.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// Estados de importación
sealed class ImportacionState {
    object Inicial : ImportacionState()
    object SeleccionandoArchivo : ImportacionState()
    data class ArchivoSeleccionado(val nombreArchivo: String, val totalRegistros: Int) :
        ImportacionState()

    data class Validando(val progreso: Float) : ImportacionState()
    data class ValidacionCompleta(
        val validos: Int,
        val errores: List<ErrorValidacion>,
        val preview: List<EstudiantePreview>
    ) : ImportacionState()

    data class Importando(val progreso: Float) : ImportacionState()
    data class ImportacionCompleta(val exitosos: Int, val fallidos: Int) : ImportacionState()
    data class Error(val mensaje: String) : ImportacionState()
}

data class ErrorValidacion(
    val fila: Int,
    val campo: String,
    val mensaje: String
)

data class EstudiantePreview(
    val fila: Int,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val codigoSiagie: String,
    val grado: String,
    val seccion: String,
    val esValido: Boolean,
    val errores: List<String> = emptyList()
)

@Composable
fun StudentImportScreen(
    onNavigateBack: () -> Unit = {},
    onImportacionCompleta: (Int) -> Unit = {}
) {
    var importacionState by remember { mutableStateOf<ImportacionState>(ImportacionState.Inicial) }

    // Datos simulados para preview
    val estudiantesPreview = remember {
        listOf(
            EstudiantePreview(2, "Juan Carlos", "García", "López", "00123456", "1", "A", true),
            EstudiantePreview(
                3,
                "María Elena",
                "Rodríguez",
                "Martínez",
                "00123457",
                "1",
                "A",
                true
            ),
            EstudiantePreview(
                4,
                "Pedro",
                "",
                "Sánchez",
                "00123458",
                "1",
                "B",
                false,
                listOf("Apellido materno vacío")
            ),
            EstudiantePreview(
                5,
                "Ana Lucía",
                "Torres",
                "Ramírez",
                "",
                "2",
                "A",
                false,
                listOf("Código SIAGIE vacío")
            ),
            EstudiantePreview(6, "Carlos Alberto", "Vega", "Flores", "00123460", "2", "B", true)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Importar desde Excel",
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
                    if (importacionState is ImportacionState.ValidacionCompleta) {
                        IconButton(
                            onClick = { /* Descargar plantilla de errores */ }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = "Descargar errores"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = importacionState) {
                is ImportacionState.Inicial -> {
                    PantallaInicial(
                        onSeleccionarArchivo = {
                            // Simular selección de archivo
                            importacionState = ImportacionState.ArchivoSeleccionado(
                                nombreArchivo = "padron_estudiantes.xlsx",
                                totalRegistros = 5
                            )
                        },
                        onDescargarPlantilla = { /* Descargar plantilla */ }
                    )
                }

                is ImportacionState.ArchivoSeleccionado -> {
                    ArchivoSeleccionadoView(
                        nombreArchivo = state.nombreArchivo,
                        totalRegistros = state.totalRegistros,
                        onValidar = {
                            importacionState = ImportacionState.Validando(0f)
                            // Simular validación
                            kotlinx.coroutines.MainScope().launch {
                                kotlinx.coroutines.delay(2000)
                                importacionState = ImportacionState.ValidacionCompleta(
                                    validos = 3,
                                    errores = listOf(
                                        ErrorValidacion(4, "Apellido Materno", "Campo vacío"),
                                        ErrorValidacion(5, "Código SIAGIE", "Campo vacío")
                                    ),
                                    preview = estudiantesPreview
                                )
                            }
                        },
                        onCancelar = {
                            importacionState = ImportacionState.Inicial
                        }
                    )
                }

                is ImportacionState.Validando -> {
                    ValidandoView(progreso = state.progreso)
                }

                is ImportacionState.ValidacionCompleta -> {
                    ValidacionCompletaView(
                        validos = state.validos,
                        errores = state.errores,
                        preview = state.preview,
                        onConfirmarImportacion = {
                            importacionState = ImportacionState.Importando(0f)
                            // Simular importación
                            kotlinx.coroutines.MainScope().launch {
                                kotlinx.coroutines.delay(2000)
                                importacionState = ImportacionState.ImportacionCompleta(
                                    exitosos = 3,
                                    fallidos = 2
                                )
                            }
                        },
                        onCancelar = {
                            importacionState = ImportacionState.Inicial
                        }
                    )
                }

                is ImportacionState.Importando -> {
                    ImportandoView(progreso = state.progreso)
                }

                is ImportacionState.ImportacionCompleta -> {
                    ImportacionCompletaView(
                        exitosos = state.exitosos,
                        fallidos = state.fallidos,
                        onFinalizar = {
                            onImportacionCompleta(state.exitosos)
                            onNavigateBack()
                        }
                    )
                }

                is ImportacionState.Error -> {
                    ErrorView(
                        mensaje = state.mensaje,
                        onReintentar = {
                            importacionState = ImportacionState.Inicial
                        }
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun PantallaInicial(
    onSeleccionarArchivo: () -> Unit,
    onDescargarPlantilla: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.UploadFile,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Importar Padrón de Estudiantes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sube un archivo Excel (.xlsx) con los datos de tus estudiantes",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card de instrucciones
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Formato requerido",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                InstruccionItem("Nombres")
                InstruccionItem("Apellido Paterno")
                InstruccionItem("Apellido Materno")
                InstruccionItem("Código SIAGIE (8 dígitos)")
                InstruccionItem("Grado (1-6)")
                InstruccionItem("Sección (A-F)")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón principal
        Button(
            onClick = onSeleccionarArchivo,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.FileOpen,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Seleccionar archivo Excel",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón secundario
        OutlinedButton(
            onClick = onDescargarPlantilla,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Download,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Descargar plantilla")
        }
    }
}

@Composable
private fun InstruccionItem(texto: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ArchivoSeleccionadoView(
    nombreArchivo: String,
    totalRegistros: Int,
    onValidar: () -> Unit,
    onCancelar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Description,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Archivo seleccionado",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = nombreArchivo,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$totalRegistros registros detectados",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onValidar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Validar datos",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Cancelar")
        }
    }
}

@Composable
private fun ValidandoView(progreso: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            strokeWidth = 6.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Validando datos...",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Verificando formato y datos requeridos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ValidacionCompletaView(
    validos: Int,
    errores: List<ErrorValidacion>,
    preview: List<EstudiantePreview>,
    onConfirmarImportacion: () -> Unit,
    onCancelar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Resumen de validación
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (errores.isEmpty())
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (errores.isEmpty()) "Validación exitosa" else "Errores encontrados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (errores.isEmpty())
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$validos válidos • ${errores.size} con errores",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (errores.isEmpty())
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Icon(
                    imageVector = if (errores.isEmpty()) Icons.Filled.CheckCircle else Icons.Filled.Error,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = if (errores.isEmpty())
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        // Lista de preview
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Vista previa de registros",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(preview) { estudiante ->
                EstudiantePreviewCard(estudiante = estudiante)
            }
        }

        // Botones de acción
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 3.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = onConfirmarImportacion,
                    modifier = Modifier.weight(1f),
                    enabled = validos > 0,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Upload,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Importar ($validos)")
                }
            }
        }
    }
}

@Composable
private fun EstudiantePreviewCard(
    estudiante: EstudiantePreview
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (estudiante.esValido)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        ),
        border = if (!estudiante.esValido)
            androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (estudiante.esValido) Icons.Filled.CheckCircle else Icons.Filled.Error,
                contentDescription = null,
                tint = if (estudiante.esValido)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${estudiante.apellidoPaterno} ${estudiante.apellidoMaterno}, ${estudiante.nombres}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Código: ${estudiante.codigoSiagie.ifEmpty { "---" }} • ${estudiante.grado}° ${estudiante.seccion}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (estudiante.errores.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    estudiante.errores.forEach { error ->
                        Text(
                            text = "• $error",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Text(
                text = "Fila ${estudiante.fila}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ImportandoView(progreso: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            strokeWidth = 6.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Importando estudiantes...",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Por favor espera mientras guardamos los datos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ImportacionCompletaView(
    exitosos: Int,
    fallidos: Int,
    onFinalizar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Importación completada",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$exitosos",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Exitosos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    if (fallidos > 0) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$fallidos",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = "Omitidos",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onFinalizar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Finalizar",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun ErrorView(
    mensaje: String,
    onReintentar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Error en la importación",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reintentar")
        }
    }
}