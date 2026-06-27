class BankAccount {
    private String owner;
    private String accountId;
    private double balance;

    public BankAccount(String owner, String accountId) {
        this.owner = owner;
        this.accountId = accountId;
        this.balance = 0;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }

    public void showInfo() {
        System.out.println("帳戶：" + accountId +
                " 戶名：" + owner +
                " 餘額：" + balance);
    }
}

public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String owner, String accountId, double interestRate) {
        super(owner, accountId);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate;
        System.out.println("利息 " + interest + " 已存入");
        deposit(interest);
    }

    public static void main(String[] args) {
        SavingsAccount acc = new SavingsAccount("李小華", "S001", 0.02);

        acc.deposit(10000);
        acc.showInfo();

        acc.addInterest();

        acc.showInfo();
    }
}
