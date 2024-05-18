package br.lucas.olivier.pixsimulator.msconta.application.enums;

public enum TopicsEnum {
    CONTA_MOVIMENTACAO("pix.simulator.conta.movimentacao.topic");

    private final String topic;

    TopicsEnum(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
