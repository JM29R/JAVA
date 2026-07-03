package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model;


public record Question( String value) {
    public Question {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Question cannot be blank.");
        }
    }
}
