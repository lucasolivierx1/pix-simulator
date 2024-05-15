package br.lucas.olivier.pixsimulator.msconta.domain.exceptions;

public class PixSimulatorException extends RuntimeException {

    public PixSimulatorException(String message) {
        super(message);
    }

    public PixSimulatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
