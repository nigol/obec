package cz.nigol.obec.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@NamedQueries({
@NamedQuery(name=Payment.GET_ALL, query="SELECT p FROM Payment p"),
@NamedQuery(name=Payment.GET_FOR_USER, query="SELECT p FROM Payment p WHERE p.forUser = :user ORDER BY a.changedAt DESC "),
})
@Entity
@Table(name = "OB_PAYMENT")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL = "Payment.GET_ALL";
	public static final String GET_FOR_USER = "Payment.GET_FOR_USER";

	public static final String USER_PARAM = "user";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;

	@Column(name="LABEL", columnDefinition="VARCHAR(100)")
	private String label;

	@Column(name="CHANGED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changedAt;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User changedBy;

	@ManyToOne
	@JoinColumn(name="PAYMENT_TYPE_ID")
	private PaymentType paymentType;

	@ManyToOne
	@JoinColumn(name="FOR_USER_ID")
	private User forUser;

	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="YEAR")
	private int year;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public User getForUser() {
		return forUser;
	}
 
	public void setForUser(User forUser) {
		this.forUser = forUser;
	 }

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public User getChangedBy() {
		return changedBy;
	}
 
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	 }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Payment that = (Payment) o;
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