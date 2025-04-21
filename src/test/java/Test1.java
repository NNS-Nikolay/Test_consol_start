import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class Test1 {


    @Test
    @DisplayName("Авторизация под пользователем standart")
    @Severity(SeverityLevel.BLOCKER)
    public void standartAutorizationTest() {
        accertEquqls(1, 1);
    }

    private void accertEquqls(int v, double a) {
    }


}


