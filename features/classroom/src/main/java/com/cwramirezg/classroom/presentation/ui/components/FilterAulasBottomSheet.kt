@file:OptIn(ExperimentalMaterial3Api::class)

package com.cwramirezg.classroom.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cwramirezg.classroom.domain.model.NivelEducativo
import com.cwramirezg.classroom.domain.model.Turno

@Composable
fun FilterAulasBottomSheet(
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

@Preview
@Composable
private fun FilterAulasBottomSheetPreview() {
    FilterAulasBottomSheet(
        selectedNivel = NivelEducativo.INICIAL,
        selectedTurno = Turno.MANANA,
        onNivelSelected = {},
        onTurnoSelected = {},
        onDismiss = {},
        onClearFilters = {},
    )
}