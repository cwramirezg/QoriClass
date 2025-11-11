package com.cwramirezg.classroom.domain.model

data class Horario(
    val id: String,
    val diaSemana: DiaSemana,
    val horaInicio: String, // HH:mm
    val horaFin: String, // HH:mm
    val areaCurricular: String,
    val docente: String?
)
