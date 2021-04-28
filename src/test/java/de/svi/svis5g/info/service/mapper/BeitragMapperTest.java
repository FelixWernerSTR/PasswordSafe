package de.svi.svis5g.info.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeitragMapperTest {

    private BeitragMapper beitragMapper;

    @BeforeEach
    public void setUp() {
        beitragMapper = new BeitragMapperImpl();
    }
}
