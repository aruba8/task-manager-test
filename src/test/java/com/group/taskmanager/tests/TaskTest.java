package com.group.taskmanager.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class TaskTest extends BaseTest {


    @Test
    public void addTask() throws Exception {
        String taskName = "New Task #1";
        createTask(taskName);
        $(By.xpath("//span[contains(text(), 'Planned:')]")).shouldHave(Condition.text("Planned: 2"));
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article/div[contains(text(), '" + taskName + "')]")).should(Condition.exist);
    }

    @Test
    public void updateTask() throws Exception {
        String taskName = "Task to update";
        createTask(taskName);
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article/div[contains(text(), '" + taskName + "')]")).hover();
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article[div[contains(text(), '" + taskName + "')]]//span/i")).click();
        SelenideElement modalWindow = $(".modal-dialog");
        modalWindow.$("h4").shouldHave(Condition.text("Edit task"));
        modalWindow.$("#input-status").selectOption("In Progress");
        modalWindow.$(By.xpath("//button[text()='Save']")).click();
        $(By.xpath("//span[contains(text(), 'In Progress:')]")).shouldHave(Condition.text("In Progress: 2"));
        $(By.xpath("//section[div[strong[text()='In Progress']]]/div/article/div[contains(text(), '" + taskName + "')]")).should(Condition.exist);
    }

    @Test
    public void deleteTask() throws Exception {
        String taskName = "Task to delete";
        createTask(taskName);
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article/div[contains(text(), '" + taskName + "')]")).hover();
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article[div[contains(text(), '" + taskName + "')]]//span/i")).click();
        SelenideElement modalWindow = $(".modal-dialog");
        modalWindow.$("h4").shouldHave(Condition.text("Edit task"));
        modalWindow.$(By.xpath("//button[contains(@class, 'delete')]")).click();
        $(By.xpath("//section[div[strong[text()='Planned']]]/div/article/div[contains(text(), '" + taskName + "')]")).shouldNot(Condition.exist);
    }

    private void createTask(String taskName) {
        $(By.xpath("//button[text()='New Task']")).click();
        SelenideElement modalWindow = $(".modal-dialog");
        modalWindow.$("#input-title").sendKeys(taskName);
        modalWindow.$("#input-description").sendKeys("Description :" + taskName);
        modalWindow.$("#input-estimate").sendKeys("2");
        modalWindow.$("#input-status").selectOption("Planned");
        modalWindow.$(".btn-success").shouldBe(Condition.enabled);
        modalWindow.$(".btn-success").click();
    }
}
