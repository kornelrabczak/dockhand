package com.thecookiezen.bussiness.deployment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class PortMapping {
    enum Protocol {
        TCP("tcp"), UDP("udp");

        private final String type;

        Protocol(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private final int internalPort;
    private final Integer externalPort;
    private final Protocol protocol;
}
