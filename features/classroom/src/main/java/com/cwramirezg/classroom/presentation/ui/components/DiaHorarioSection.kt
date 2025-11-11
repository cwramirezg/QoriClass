package com.cwramirezg.classroom.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cwramirezg.classroom.domain.model.DiaSemana
import com.cwramirezg.classroom.domain.model.Horario

@Composable
fun DiaHorarioSection(
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

@Preview
@Composable
private fun DiaHorarioSectionPreview() {
    DiaHorarioSection(
        dia = DiaSemana.LUNES,
        horarios = listOf(
            Horario(
                id = "h1",
                diaSemana = DiaSemana.LUNES,
                horaInicio = "08:00",
                horaFin = "09:30",
                areaCurricular = "Matemática",
                docente = "Prof. Juan Pérez"
            )
        ),
        onEditHorario = {},
        onDeleteHorario = {},
    )
}
