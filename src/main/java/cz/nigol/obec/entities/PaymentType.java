package cz.nigol.obec.entities;

import java.io.Serializable;
import javax.persistence.*;

@NamedQueries({
@NamedQuery(name=PaymentType.GET_ALL, query="SELECT p FROM PaymentType p"),
})
@Entity
@Table(name = "OB_PAYMENT_TYPE")
public class PaymentType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL = "PaymentType.GET_ALL";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;

	@Column(name="SYMBOL")
	private int symbol;

	@Column(name="LABEL", columnDefinition="VARCHAR(300)")
	private String label;

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

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