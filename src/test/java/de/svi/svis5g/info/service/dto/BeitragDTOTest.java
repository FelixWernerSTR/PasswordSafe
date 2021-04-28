package de.svi.svis5g.info.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.info.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeitragDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeitragDTO.class);
        BeitragDTO beitragDTO1 = new BeitragDTO();
        beitragDTO1.setId(1L);
        BeitragDTO beitragDTO2 = new BeitragDTO();
        assertThat(beitragDTO1).isNotEqualTo(beitragDTO2);
        beitragDTO2.setId(beitragDTO1.getId());
        assertThat(beitragDTO1).isEqualTo(beitragDTO2);
        beitragDTO2.setId(2L);
        assertThat(beitragDTO1).isNotEqualTo(beitragDTO2);
        beitragDTO1.setId(null);
        assertThat(beitragDTO1).isNotEqualTo(beitragDTO2);
    }
}
