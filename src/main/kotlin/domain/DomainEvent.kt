package domain

import java.util.Date

interface DomainEvent {
    fun occuredOn(): Date
}