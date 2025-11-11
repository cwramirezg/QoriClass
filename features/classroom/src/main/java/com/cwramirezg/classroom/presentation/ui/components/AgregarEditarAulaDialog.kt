@file:OptIn(ExperimentalMaterial3Api::class)

package com.cwramirezg.classroom.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cwramirezg.classroom.domain.model.Aula
import com.cwramirezg.classroom.domain.model.NivelEducativo
import com.cwramirezg.classroom.domain.model.Turno

@Composable
fun AgregarEditarAulaDialog(
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

@Preview
@Composable
private fun AgregarEditarAulaDialogPreview() {
    AgregarEditarAulaDialog(
        aula = null,
        onDismiss = {},
        onConfirm = {}
    )
}