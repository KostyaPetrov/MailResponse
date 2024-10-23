package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReviewQuestionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String textReview;
    private Long questionId;
    private Long userId;

    // Конструктор без параметров
    public ReviewQuestionDTO() {
    }

    // Геттеры и сеттеры
    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Переопределение методов equals и hashCode (необязательно, но рекомендуется)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewQuestionDTO that = (ReviewQuestionDTO) o;

        if (!textReview.equals(that.textReview)) return false;
        if (!questionId.equals(that.questionId)) return false;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = textReview.hashCode();
        result = 31 * result + questionId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }

    // Метод toString() для удобного отображения объекта (необязательно)
    @Override
    public String toString() {
        return "ReviewQuestionDTO{" +
                "textReview='" + textReview + '\'' +
                ", questionId=" + questionId +
                ", userId=" + userId +
                '}';
    }
}
