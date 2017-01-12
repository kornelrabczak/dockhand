package com.thecookiezen.bussiness.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PortMapping implements Serializable {
    private static final long serialVersionUID = 1L;
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

    private final String name;
    private final int internalPort;
    private final Integer externalPort;
    private final Protocol protocol;
}
