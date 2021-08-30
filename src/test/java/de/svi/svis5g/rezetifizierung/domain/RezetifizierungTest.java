package de.svi.svis5g.rezetifizierung.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.rezetifizierung.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RezetifizierungTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rezetifizierung.class);
        Rezetifizierung rezetifizierung1 = new Rezetifizierung();
        rezetifizierung1.setId(1L);
        Rezetifizierung rezetifizierung2 = new Rezetifizierung();
        rezetifizierung2.setId(rezetifizierung1.getId());
        assertThat(rezetifizierung1).isEqualTo(rezetifizierung2);
        rezetifizierung2.setId(2L);
        assertThat(rezetifizierung1).isNotEqualTo(rezetifizierung2);
        rezetifizierung1.setId(null);
        assertThat(rezetifizierung1).isNotEqualTo(rezetifizierung2);
    }
}
