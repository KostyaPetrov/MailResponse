package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import lombok.AllArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;

import java.io.Serializable;
public class QuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String textQuestion;
    private long userId;
    private Integer countReview;
    private PermissionStatus permissionStatus;

    // Конструктор без параметров
    public QuestionDTO() {
    }

    // Геттеры и сеттеры

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getCountReview() {
        return countReview;
    }

    public void setCountReview(Integer countReview) {
        this.countReview = countReview;
    }

    public PermissionStatus getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(PermissionStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    // Переопределение методов equals и hashCode (необязательно, но рекомендуется)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionDTO that = (QuestionDTO) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (!textQuestion.equals(that.textQuestion)) return false;
        if (!countReview.equals(that.countReview)) return false;
        return permissionStatus == that.permissionStatus;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + textQuestion.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + countReview.hashCode();
        result = 31 * result + permissionStatus.hashCode();
        return result;
    }

    // Метод toString() для удобного отображения объекта (необязательно)

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id=" + id +
                ", textQuestion='" + textQuestion + '\'' +
                ", userId=" + userId +
                ", countReview=" + countReview +
                ", permissionStatus=" + permissionStatus +
                '}';
    }
}