package de.svi.svis5g.info.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThemaMapperTest {

    private ThemaMapper themaMapper;

    @BeforeEach
    public void setUp() {
        themaMapper = new ThemaMapperImpl();
    }
}
