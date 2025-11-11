package com.cwramirezg.classroom.domain.model

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
