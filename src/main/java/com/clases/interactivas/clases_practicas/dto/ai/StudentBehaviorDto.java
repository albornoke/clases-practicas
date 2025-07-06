package com.clases.interactivas.clases_practicas.dto.ai;

// Consider using Lombok for boilerplate code reduction (@Data, @NoArgsConstructor, @AllArgsConstructor)
public class StudentBehaviorDto {

    private Long studentId;
    private String behaviorDescription;
    private java.time.LocalDateTime timestamp;
    // Agrega aquí otros campos relevantes que describan el comportamiento del estudiante

    public StudentBehaviorDto() {
    }

    public StudentBehaviorDto(Long studentId, String behaviorDescription, java.time.LocalDateTime timestamp) {
        this.studentId = studentId;
        this.behaviorDescription = behaviorDescription;
        this.timestamp = timestamp;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public java.time.LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.time.LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StudentBehaviorDto{" +
                "studentId=" + studentId +
                ", behaviorDescription='" + behaviorDescription + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}