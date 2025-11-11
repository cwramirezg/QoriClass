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
import com.cwramirezg.classroom.domain.model.DiaSemana
import com.cwramirezg.classroom.domain.model.Horario

@Composable
fun AgregarEditarHorarioDialog(
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

@Preview
@Composable
private fun AgregarEditarHorarioDialogPreview() {
    AgregarEditarHorarioDialog(
        horario = null,
        onDismiss = {},
        onConfirm = {}
    )
}