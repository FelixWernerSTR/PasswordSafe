package de.svi.svis5g.info.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.info.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThemaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemaDTO.class);
        ThemaDTO themaDTO1 = new ThemaDTO();
        themaDTO1.setId(1L);
        ThemaDTO themaDTO2 = new ThemaDTO();
        assertThat(themaDTO1).isNotEqualTo(themaDTO2);
        themaDTO2.setId(themaDTO1.getId());
        assertThat(themaDTO1).isEqualTo(themaDTO2);
        themaDTO2.setId(2L);
        assertThat(themaDTO1).isNotEqualTo(themaDTO2);
        themaDTO1.setId(null);
        assertThat(themaDTO1).isNotEqualTo(themaDTO2);
    }
}
