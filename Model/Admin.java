package Model;

public class Admin extends ProgramUser {

    public Admin() {

    }

    public Admin(String username, String accountPassword, String securityQuestion, String securityPassword, int userType) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;
        
        // add to database
    }

    @Override
    public Admin fetchUser(String username, String password) {
        // find the matched username and password of userType = 1 in the database
            // return null if wala,
            // return the whole user if meron (populate an instance of the user then return it)
            return null;
    }

    @Override
    public void createQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
    }
}