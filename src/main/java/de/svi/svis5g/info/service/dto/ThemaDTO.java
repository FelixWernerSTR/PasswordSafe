package de.svi.svis5g.info.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.svi.svis5g.info.domain.Thema} entity.
 */
public class ThemaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 30)
    private String rechte;

    @Min(value = 0L)
    @Max(value = 999L)
    private Long displaycount;

    private ThementypDTO thementyp;

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

    public String getRechte() {
        return rechte;
    }

    public void setRechte(String rechte) {
        this.rechte = rechte;
    }

    public Long getDisplaycount() {
        return displaycount;
    }

    public void setDisplaycount(Long displaycount) {
        this.displaycount = displaycount;
    }

    public ThementypDTO getThementyp() {
        return thementyp;
    }

    public void setThementyp(ThementypDTO thementyp) {
        this.thementyp = thementyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemaDTO)) {
            return false;
        }

        ThemaDTO themaDTO = (ThemaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, themaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rechte='" + getRechte() + "'" +
            ", displaycount=" + getDisplaycount() +
            ", thementyp=" + getThementyp() +
            "}";
    }
}
