package de.svi.svis5g.info.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.svi.svis5g.info.domain.Thementyp} entity.
 */
public class ThementypDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThementypDTO)) {
            return false;
        }

        ThementypDTO thementypDTO = (ThementypDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thementypDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThementypDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
