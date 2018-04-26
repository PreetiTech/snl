package snlTest;

import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BoardTest {

	Board board;

	// @BeforeTest
	public void createInstance() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		board = new Board();
	}

	// @Test
	public void parameterizdBoardTest() {
		try {
			Board board = new Board();
			try {
				board.registerPlayer("Priya");
			} catch (PlayerExistsException | GameInProgressException | MaxPlayersReachedExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test Case For MaxPlayersReachedException Class i.e If we enter more than 4
	 * players
	 */
	@Test
	public void maxPlayersReachedExceptionTest() {
		int num = 11;
		MaxPlayersReachedExeption maxPlayerReachedException = new MaxPlayersReachedExeption(num);
		Assert.assertEquals("The board already has maximum allowed Player: " + num,
				maxPlayerReachedException.getMessage());

	}

	/* Test Case For GameInProgressException Class */
	@Test
	public void gameInProgressExceptionTest() {
		GameInProgressException gameInProgressException = new GameInProgressException();
		Assert.assertEquals("New player cannot join since the game has started", gameInProgressException.getMessage());
	}

	/* Test Cases to check if a player initial position is zero or not */
	@Test
	public void registeredPlayerTestPosition() {
		try {
			String name = "shikha";
			JSONArray newplayer = board.registerPlayer(name);
			JSONObject json = (JSONObject) newplayer.get(0);
			Assert.assertEquals(0, json.get("position"));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}

	/* Test Cases to check if registering more than four players */
	@Test
	public void moreThanFourPlayers() {
		try {
			Board b1 = new Board();
			b1.registerPlayer("Jhon");
			b1.registerPlayer("Steve");
			b1.registerPlayer("Paul");
			b1.registerPlayer("Joesph");
			b1.registerPlayer("Crist");

		} catch (Exception ex) {
			Logger.log("****** " + ex.getMessage());
			Assert.assertEquals("The board already has maximum allowed Player: " + 4, ex.getMessage());
		}
	}

	/* Test Cases to check if registering four players */
	@Test
	public void fourPlayersRegister() {
		try {
			Board b1 = new Board();

			b1.registerPlayer("Jhon");
			b1.registerPlayer("Steve");
			b1.registerPlayer("Paul");
			b1.registerPlayer("Crist");
			Logger.log("Four Player Sucessfully registered.");

		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}

	/* Test Cases to check if already registered player is register again or not */
	@Test
	public void samePlayerRegisterationCheck() {
		String name = "Ram";
		try {
			Board board = new Board();
			board.registerPlayer(name);
			board.registerPlayer(name);
		} catch (Exception ex) {
			String msg = ex.getMessage();
			assertEquals("Player '" + name + "' already exists on board", msg);
			// Logger.error(ex.getMessage());
		}
	}

	/* Test to check if player is registering with valid name or not */
	@Test
	public void registeredPlayerTestName() {
		try {
			Board board = new Board();
			String name = "shikha";
			JSONArray newplayer = board.registerPlayer("shikha");
			JSONObject json = (JSONObject) newplayer.get(0);
			System.out.println(json.get("name"));
			Assert.assertEquals("shikha", json.get("name"));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}

	/* Test cases to check if uuid is valid alphanumeric or not */
	@Test
	public void registeredPlayerTestOnUuid() {
		try {
			Board board = new Board();
			String regex = "^[a-zA-Z0-9-]*$";
			JSONArray newplayer = board.registerPlayer("kavita");
			JSONObject json = (JSONObject) newplayer.get(0);
			String uuid = (String) json.get("uuid");
			Assert.assertEquals(true, uuid.matches(regex));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}

	/* Testcase to check delete player */
	@Test
	public void testDeletePlayer() {
		try {
			// Registering new Players
			Board bd = new Board();
			boolean flag = true;
			bd.registerPlayer("Raju");
			bd.registerPlayer("Ashima");
			JSONArray playersJson = bd.registerPlayer("Lavanya");

			// Fetching first player from json
			JSONObject json = (JSONObject) playersJson.get(0);
			String playerUuid = (String) json.get("uuid");
			UUID deletedPlayerUuid = UUID.fromString(playerUuid);

			JSONArray jsonAfterDeletion = bd.deletePlayer(deletedPlayerUuid);
			System.out.println("Json After Deleting Player.");
			for (int i = 0; i < jsonAfterDeletion.length(); i++) {
				// System.out.println("^^^^^^^^^^^^^"+jsonAfterDeletion+"^^^^^^^^^^^^^");
				JSONObject obj = (JSONObject) jsonAfterDeletion.get(i);

				if (obj.get("uuid").equals(deletedPlayerUuid)) {
					flag = true;
					break;
					// Assert.assertEquals(false, check);
				}
			}
			if (flag == false) {
				// Assert.assertEquals(true, false);
				System.out.println("Test Case Failed");
			} else {
				// Assert.assertEquals(true, true);
				System.out.println("Test Case Failed");
			}
		} catch (Exception ex) {
			Logger.error(ex.getMessage());
		}
	}

	/* Test case to check already Deleted Player */
	@Test
	public void alreadyDeletedPlayer() {
		try {
			Board brd = new Board();
			brd.registerPlayer("Aasif");
			brd.registerPlayer("Sharza");
			JSONArray threePlayersJson = brd.registerPlayer("Fatima");
			System.out.println(threePlayersJson + "^^^^^^^^^^^^^^^^^$$$$$$$$$$$$$$$$$$$$$$");
			JSONObject obj = threePlayersJson.getJSONObject(0);
			String str = obj.getString("uuid");
			UUID uuid = UUID.fromString(str);
			JSONArray deletePlayer = brd.deletePlayer(uuid);

			System.out.println(deletePlayer + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		} catch (Exception e) {
			Logger.error(e);
		}
	}

	/* Test case to check turn is valid or not in rollDice Method */
	@Test
	public void rollDiceTest() {
		try {
			Board brd = new Board();
			brd.registerPlayer("Supriya");
			JSONArray twoplayersjson = brd.registerPlayer("Parveen");

			JSONObject playerOneJson = twoplayersjson.getJSONObject(0);
			String str = playerOneJson.getString("uuid");
			UUID playerOneUuid = UUID.fromString(str);

			JSONObject playerTwoJson = twoplayersjson.getJSONObject(1);
			String str2 = playerTwoJson.getString("uuid");
			UUID playerTwoUuid = UUID.fromString(str2);

			Object turn1 = brd.getData().get("turn");
			Assert.assertEquals(0, turn1);
			JSONObject playerObj1 = brd.rollDice(playerOneUuid);
			turn1 = brd.getData().get("turn");
			Assert.assertEquals(1, turn1);
			JSONObject playerObj2 = brd.rollDice(playerTwoUuid);
			turn1 = brd.getData().get("turn");
			Assert.assertEquals(0, turn1);

		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}

	/* Test case to check if one player played twice */
	@Test
	public void rollDiceTestPlayerPlayedTwice() {
		UUID playerOneUuid = null;
		try {
			Board bd = new Board();
			JSONArray registerPlayer = bd.registerPlayer("Deepak");
			bd.registerPlayer("Manish");
			JSONObject json = registerPlayer.getJSONObject(0);
			String str = json.getString("uuid");
			playerOneUuid = UUID.fromString(str);
			bd.rollDice(playerOneUuid);
			bd.rollDice(playerOneUuid);

		} catch (Exception ex) {
			Assert.assertEquals("Player '" + playerOneUuid.toString() + "' does not have the turn", ex.getMessage());
			// Logger.error(ex.getMessage());
		}
	}
}
