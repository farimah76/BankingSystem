package azki.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NonNull
    @NotBlank(message = "please enter your account Holder Name")
    private String accountHolderName;
    @Column
    private int accountNumber;
    @Column
    private double balance;

}
