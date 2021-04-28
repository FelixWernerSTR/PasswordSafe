package de.svi.svis5g.info.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.info.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThementypDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThementypDTO.class);
        ThementypDTO thementypDTO1 = new ThementypDTO();
        thementypDTO1.setId(1L);
        ThementypDTO thementypDTO2 = new ThementypDTO();
        assertThat(thementypDTO1).isNotEqualTo(thementypDTO2);
        thementypDTO2.setId(thementypDTO1.getId());
        assertThat(thementypDTO1).isEqualTo(thementypDTO2);
        thementypDTO2.setId(2L);
        assertThat(thementypDTO1).isNotEqualTo(thementypDTO2);
        thementypDTO1.setId(null);
        assertThat(thementypDTO1).isNotEqualTo(thementypDTO2);
    }
}
