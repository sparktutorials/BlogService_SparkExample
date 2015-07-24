package me.tomassetti;

public class Answer {

    public Answer(int code) {
        this.code = code;
        this.body = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (code != answer.code) return false;
        if (body != null ? !body.equals(answer.body) : answer.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer(code=" + code + ", body=" + body + ")";
    }

    public String getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    private int code;
    private String body;

    public Answer(int code, String body){
        this.code = code;
        this.body = body;
    }

    public static Answer ok(String body) {
        return new Answer(200, body);
    }
}
