package engine.Entities;

public class Feedback {

    private   boolean success;
    private String feedback;

    public Feedback(boolean success)
    {
            this.success = success;
            this.feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";




    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
