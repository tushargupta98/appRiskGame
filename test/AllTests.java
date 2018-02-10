import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import game.risk.controller.GameControllerTest;
import game.risk.model.Helper.AttackHelperTest;
import game.risk.model.Helper.GamePhaseHelperTest;
import game.risk.model.valueobjects.PlayerTest;
import game.risk.view.GameMapSetupViewTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({PlayerTest.class, GameMapSetupViewTest.class, AttackHelperTest.class,
    GamePhaseHelperTest.class, GameControllerTest.class})
/**
 * Main driver Test class to run all the Individual Test cases
 * 
 * @author karthik
 *
 */
public class AllTests {

}
