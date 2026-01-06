package cz.nigol.obec.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TYPE")
public class PaymentType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(length = 300)
	private String label;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PaymentType that = (PaymentType) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

	@Override
	public String toString() {
		return "PaymentType{" +
			"id=" + id +
			", label='" + label + '\'' +
			'}';
	}
}