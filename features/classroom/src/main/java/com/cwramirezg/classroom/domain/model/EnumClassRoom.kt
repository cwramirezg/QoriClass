package com.cwramirezg.classroom.domain.model

enum class NivelEducativo(val displayName: String) {
    INICIAL("Inicial"),
    PRIMARIA("Primaria"),
    SECUNDARIA("Secundaria")
}

enum class Turno(val displayName: String) {
    MANANA("Mañana"),
    TARDE("Tarde"),
    NOCHE("Noche")
}

enum class DiaSemana(val displayName: String, val abreviatura: String) {
    LUNES("Lunes", "L"),
    MARTES("Martes", "M"),
    MIERCOLES("Miércoles", "X"),
    JUEVES("Jueves", "J"),
    VIERNES("Viernes", "V")
}