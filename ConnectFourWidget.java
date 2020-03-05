package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {
	
	private enum Player {RED, BLACK};
	
	private JSpotBoard _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	private Color _winner_color;
	private boolean _is_draw;
	private int _x;
	private int _y;
	private boolean _fourInARow;
	
	public ConnectFourWidget()
	{
		_board = new JSpotBoard(7,6);
		
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (j % 2 == 1)
				{
					_board.getSpotAt(j, i).setSpotColor(Color.GRAY);
					_board.getSpotAt(j, i).setBackground(Color.GRAY);
				}
				else
				{
					_board.getSpotAt(j, i).setSpotColor(Color.LIGHT_GRAY);
					_board.getSpotAt(j, i).setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		
		_message = new JLabel();
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
		
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());
		
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);
		
		add(reset_message_panel, BorderLayout.SOUTH);
		
		_board.addSpotListener(this);
		
		resetGame();
	}
	
	private void resetGame()
	{
		_game_won = false;
		_is_draw = false;
		_next_to_play = Player.RED;
		_message.setText("Welcome to Connect Four! Red goes first.");
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (j % 2 == 1)
				{
					_board.getSpotAt(j, i).setSpotColor(Color.GRAY);
					_board.getSpotAt(j, i).clearSpot();
					_board.getSpotAt(j, i).unhighlightSpot();
				}
				else
				{
					_board.getSpotAt(j, i).setSpotColor(Color.LIGHT_GRAY);
					_board.getSpotAt(j, i).clearSpot();
					_board.getSpotAt(j, i).unhighlightSpot();
				}
			}
		}
	}
	
	private boolean columnFull(Spot spot)
	{
		int j = 0;
		
		for (int i = 5; i >= 0; i--)
		{
			if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() == Color.RED || _board.getSpotAt(spot.getSpotX(), i).getSpotColor() == Color.BLACK)
			{
				j++;
				if (j == 6)
				{
					return true;
				}
			}
			
			else
			{
				return false;
			}
		
		}
		
		return false;
	}
	
	private boolean fourInARow(Spot spot, int colorCounter)
	{
		if (colorCounter == 4)
		{
			_game_won = true;
			_winner_color = spot.getSpotColor();
			return true;
		}
		return false;
	}
	
	private boolean checkWinner(Spot spot)
	{
		_x = spot.getSpotX();
		
		int colorCounter = 1;		
		//check 3 to the right
		for (int i = 1; i < 4; i++)
		{
			if ((_x+i <=6) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x+j, _y).highlightSpot();
				}
				return true;
			}
			
		}
		
		colorCounter = 1;
		
		//check 3 to the left
		for (int i = 1; i < 4; i++)
		{
			if ((_x-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x-j, _y).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 2 right and 1 left
		for (int i = 1; i < 3; i++)
		{
			if ((_x+i <= 6) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y).getSpotColor())))
			{
				colorCounter++;
			}
			
		}
		if ((_x-1 >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-1, _y).getSpotColor())))
		{
			colorCounter++;
		}
		
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x+j, _y).highlightSpot();
			}
			_board.getSpotAt(_x-1, _y).highlightSpot();
			
			return true;
		}
		
		colorCounter = 1;
		
		//check 2 left and 1 right
		for (int i = 1; i < 3; i++)
		{
			if ((_x-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_x+1 <=6) && (spot.getSpotColor().equals(_board.getSpotAt(_x+1, _y).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x-j, _y).highlightSpot();
			}
			_board.getSpotAt(_x+1, _y).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		
		//check 3 downwards
		for (int i = 1; i < 4; i++)
		{
			if ((_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x, _y+j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		//check 3 upwards
		for (int i = 1; i < 4; i++)
		{
			if ((_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x, _y-j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 2 downwards and 1 upwards
		for (int i = 1; i < 3; i++)
		{
			if ((_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		
		if ((_y-1 >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y-1).getSpotColor())))
		{
			colorCounter++;
		}
		
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x, _y+j).highlightSpot();
			}
			_board.getSpotAt(_x, _y-1).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		
		//check 2 upwards and 1 downwards
		for (int i = 1; i < 3; i++)
		{
			if ((_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_y+1 <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x, _y+1).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x, _y+j).highlightSpot();
			}
			_board.getSpotAt(_x, _y+1).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		
		//check 3 diagonal rightwards
		for (int i = 1; i < 4; i++)
		{
			if ((_x+i <= 6) && (_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x+j, _y-j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 3 diagonal leftwards
		for (int i = 1; i < 4; i++)
		{
			if ((_x-i >=0) && (_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x-j, _y+j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 2 diagonal rightwards and 1 diagonal leftwards
		for (int i = 1; i < 3; i++)
		{
			if ((_x+i <= 6) && (_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_x-1 >=0) && (_y+1 <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x-1, _y+1).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x+j, _y-j).highlightSpot();
			}
			_board.getSpotAt(_x-1, _y+1).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		//check 2 diagonal leftwards and 1 rightwards
		for (int i = 1; i < 3; i++)
		{
			if ((_x-i >= 0) && (_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_x+1 <= 6) && (_y-1 >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x+1, _y-1).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x-j, _y+j).highlightSpot();
			}
			_board.getSpotAt(_x+1, _y-1).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		//Check 3 diagonal downwards rightwards
		for (int i = 1; i < 4; i++)
		{
			if ((_x+i <= 6) && (_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x+j, _y+j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 3 diagonal leftwards
		for (int i = 1; i < 4; i++)
		{
			if ((_x-i >=0) && (_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
			
			if (fourInARow(spot, colorCounter))
			{
				for (int j = 0; j < 4; j++)
				{
					_board.getSpotAt(_x-j, _y-j).highlightSpot();
				}
				return true;
			}
		}
		
		colorCounter = 1;
		
		//check 2 diagonal rightwards down and 1 up left
		for (int i = 1; i < 3; i++)
		{
			if ((_x+i <= 6) && (_y+i <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x+i, _y+i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_x-1 >=0) && (_y-1 >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-1, _y-1).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x+j, _y+j).highlightSpot();
			}
			_board.getSpotAt(_x-1, _y-1).highlightSpot();
			return true;
		}
		
		colorCounter = 1;
		//check 2 diagonal up left and 1 down right
		for (int i = 1; i < 3; i++)
		{
			if ((_x-i >= 0) && (_y-i >= 0) && (spot.getSpotColor().equals(_board.getSpotAt(_x-i, _y-i).getSpotColor())))
			{
				colorCounter++;
			}
		}
		if ((_x+1 <= 6) && (_y+1 <= 5) && (spot.getSpotColor().equals(_board.getSpotAt(_x+1, _y+1).getSpotColor())))
		{
			colorCounter++;
		}
		if (fourInARow(spot, colorCounter))
		{
			for (int j = 0; j < 3; j++)
			{
				_board.getSpotAt(_x-j, _y-j).highlightSpot();
			}
			_board.getSpotAt(_x+1, _y+1).highlightSpot();
			return true;
		}
		
		//IF ALL ELSE FAILS
		return false;
	}
	
	private boolean fullBoard()
	{
		int i = 0;
		
		for (Spot s : _board)
		{
			if (s.getSpotColor().equals(Color.RED) || s.getSpotColor().equals(Color.BLACK))
			{
				i++;
			}
		}
		
		if (i == 42)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	private boolean checkDraw(Spot spot)
	{
		if (!checkWinner(spot) && fullBoard())
		{
			_is_draw = true;
			return true;
		}
		
		else if (!checkWinner(spot) && fullBoard())
		{
			_is_draw = false;
			return false;
		}
		
		else
		{
			_is_draw = false;
			return false;
		}
	}
	
	
	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		
		if (_game_won)
		{
			return;
		}
		
		if (_is_draw)
		{
			return;
		}
		
		if (columnFull(spot))
		{
			return;
		}
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.BLACK)
		{
			player_color = Color.BLACK;
			next_player_name = "Red";
			_next_to_play = Player.RED;
		}
		else
		{
			player_color = Color.RED;
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		}
		
		for (int i = 5; i >= 0; i--)
		{

			if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.RED && _board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.BLACK)
			{
				spot = _board.getSpotAt(spot.getSpotX(), i);//.setSpotColor(player_color);
				_y = i;
				spot.setSpotColor(player_color);
				spot.toggleSpot();
				break;
			}
		
		}
		
		checkDraw(spot);
		checkWinner(spot);
		
		if (_game_won)
		{
			for (int i = 0; i <= 5; i++)
			{
					_board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();	
			}
			_board.getSpotAt(_x, _y).highlightSpot();
			
			if (_winner_color == Color.BLACK)
			{
				_message.setText("Black wins!");
			}
			
			else 
			{
				_message.setText("Red wins!");
			}
			
		}
		
		else
		{
			if (_is_draw == true)
			{
				_message.setText("Draw game.");
			}
			
			else
			{
				_message.setText(next_player_name + "'s turn!");
			}
			
		}
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won)
		{
			return;
		}
		
		if (_is_draw)
		{
			return;
		}
		
		for (int i = 0; i <= 5; i++)
		{
			if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.RED && _board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.BLACK)
			{
				_board.getSpotAt(spot.getSpotX(), i).highlightSpot();
			}
		}
		
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		
		if (_game_won)
		{
			/*for (int i = 0; i <= 5; i++)
			{
				if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.BLACK && _board.getSpotAt(spot.getSpotX(), i).getSpotColor() != Color.RED)
				{
					_board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();
				}
			}*/
			return;
		}
		
		for (int i = 5; i >= 0; i--)
		{
			
			_board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		resetGame();
	}

	
	
}
