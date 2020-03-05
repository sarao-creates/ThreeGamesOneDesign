package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class OthelloWidget extends JPanel implements ActionListener, SpotListener {
	
	private enum Player {WHITE, BLACK};
	
	private JSpotBoard _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	private boolean _is_draw;
	private int _x;
	private int _y;

	public OthelloWidget()
	{
		_board = new JSpotBoard(8,8);
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
		for (Spot s : _board)
		{
			s.clearSpot();
			s.setSpotColor(Color.LIGHT_GRAY);
			_game_won = false;
			_is_draw = false;
			_next_to_play = Player.BLACK;
			_message.setText("Welcome to Othello. Black shall go first!");
	
		}
		
		_board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
		_board.getSpotAt(3, 3).toggleSpot();
		_board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
		_board.getSpotAt(4, 3).toggleSpot();
		_board.getSpotAt(3, 4).setSpotColor(Color.BLACK);
		_board.getSpotAt(3, 4).toggleSpot();
		_board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
		_board.getSpotAt(4, 4).toggleSpot();

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
		
		if (!highlightSpot(spot))
		{
			return;
		}
		
		if (spot.getSpotColor() == Color.BLACK || spot.getSpotColor() == Color.WHITE)
		{
			return;
		}
		
		String next_player_name = null;
		Color player_color = null;
		
		
		if (_next_to_play == Player.BLACK)
		{
			player_color = Color.BLACK;
			//next_player_name = "White";
			//_next_to_play = Player.WHITE;
		}
		else
		{
			player_color = Color.WHITE;
			//next_player_name = "Black";
			//_next_to_play = Player.BLACK;
		}
		
		
		if (highlightSpot(spot))
		{
			validSpot(spot);
			spot.setSpotColor(player_color);
			spot.toggleSpot();
			
			if (player_color == Color.BLACK)
			{
				next_player_name = "White";
				_next_to_play = Player.WHITE;
			}
			else
			{
				next_player_name = "Black";
				_next_to_play = Player.BLACK;
			}
			_message.setText(next_player_name + " to play.");
		}
		
		if (fullBoard())
		{
			winner();
		}
		
		else if (noMoreValidMoves() && !fullBoard())
		{
			if (player_color == Color.BLACK)
			{
				//player_color = Color.WHITE
				next_player_name = "White";
				_next_to_play = Player.WHITE;
			}
			else
			{
				next_player_name = "Black";
				_next_to_play = Player.BLACK;
			}
			_message.setText(next_player_name + " to play.");
			
			if (noMoreValidMoves())
			{
				winner();
			}
			else
			{
				return;
			}
		}
		
	}
	
	private void winner()
	{
		int i = 0; //black pieces.
		int j = 0; //white pieces
		
		for (Spot s : _board)
		{
			if (s.getSpotColor() == Color.BLACK)
			{
				i++;
			}
			
			else if (s.getSpotColor() == Color.WHITE)
			{
				j++;
			}
	
		}
		
		if (i > j)
		{
			_message.setText("Game Over. Black wins. Score: " + i + " to " + j);
		}
		
		else if (j > i)
		{
			_message.setText("Game Over. White wins. Score: " + j + " to " + i);
		}
		
		else
		{
			_message.setText("Game Over. Draw game.");
		}
	}
	
	private boolean fullBoard()
	{
		int i = 0;
		for (Spot s : _board)
		{
			if (s.getSpotColor() == Color.BLACK || s.getSpotColor() == Color.WHITE)
			{
				i++;
			}
		}
		
		if (i == 64)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean noMoreValidMoves()
	{
		int i = 0; //number of empty spots
		int j = 0; //numnber of spots that can't be highlighted
		for (Spot s : _board)
		{
			if (s.getSpotColor() == Color.LIGHT_GRAY)
			{
				i++;
				if (!highlightSpot(s))
				{
					j++;
				}
			}
		}
		
		if (i == j)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean highlightSpot(Spot spot)
	{
		_x = spot.getSpotX();
		_y = spot.getSpotY();
		Color spotColor;
		Color adjacentSpotColor;
		if (_next_to_play == Player.BLACK)
		{
			adjacentSpotColor = Color.WHITE;
			spotColor = Color.BLACK;
		}
		else
		{
			adjacentSpotColor = Color.BLACK;
			spotColor = Color.WHITE;
		}
	
		//test horizontally
		
		if (spot.getSpotColor() == Color.BLACK || spot.getSpotColor() == Color.WHITE)
		{
			return false;
		}

		if ((_x-1 >= 0) && (_board.getSpotAt(_x-1, _y).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _x-1; i >= 0; i--)
			{
				if (_board.getSpotAt(i, _y).getSpotColor() == spotColor)
				{
					return true;
				}
				
				else if (_board.getSpotAt(i, _y).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		else if ((_x+1 <= 7) && (_board.getSpotAt(_x+1, _y).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _x+1; i <= 7; i++)
			{
				if (_board.getSpotAt(i, _y).getSpotColor() == spotColor)
				{
					return true;
				}
				
				else if (_board.getSpotAt(i, _y).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		//test vertically
		
		if ((_y-1 >= 0) && (_board.getSpotAt(_x, _y-1).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _y-1; i >= 0; i--)
			{
				if (_board.getSpotAt(_x, i).getSpotColor() == spotColor)
				{
					return true;
				}
				else if (_board.getSpotAt(_x, i).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		else if ((_y+1 <= 7) && (_board.getSpotAt(_x, _y+1).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _y+1; i <= 7; i++)
			{
				if (_board.getSpotAt(_x, i).getSpotColor() == spotColor)
				{
					return true;
				}
				else if (_board.getSpotAt(_x, i).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		//test diagonally (bottom right)
		if ((_x+1 <= 7) && (_y+1 <= 7) &&_board.getSpotAt(_x+1, _y+1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x+i <= 7) && (_y+i <= 7) && (_board.getSpotAt(_x+i, _y+i).getSpotColor() == spotColor))
				{
					return true;
				}
				
				else if ((_x+i <= 7) && (_y+i <= 7) && (_board.getSpotAt(_x+i, _y+i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		//test diagonally (top right)
		if ((_x+1 <= 7) && (_y-1 >= 0) &&_board.getSpotAt(_x+1, _y-1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x+i <= 7) && (_y-i >= 0) && (_board.getSpotAt(_x+i, _y-i).getSpotColor() == spotColor))
				{
					return true;
				}
				
				else if ((_x+i <= 7) && (_y-i >= 0) && (_board.getSpotAt(_x+i, _y-i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		//test diagonally (top left)
		if ((_x-1 >= 0) && (_y-1 >= 0) &&_board.getSpotAt(_x-1, _y-1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x-i >= 0) && (_y-i >= 0) && (_board.getSpotAt(_x-i, _y-i).getSpotColor() == spotColor))
				{
					return true;
				}
				
				else if ((_x-i >= 0) && (_y-i >= 0) && (_board.getSpotAt(_x-i, _y-i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		if ((_x-1 >= 0) && (_y+1 <= 7) &&_board.getSpotAt(_x-1, _y+1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x-i >= 0) && (_y+i <= 7) && (_board.getSpotAt(_x-i, _y+i).getSpotColor() == spotColor))
				{
					return true;
				}
				
				else if ((_x-i >= 0) && (_y+i <= 7) && (_board.getSpotAt(_x-i, _y+i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		else
		{
			return false;
		}
		
		return false;
	}
	
	private void validSpot(Spot spot)
	{
		_x = spot.getSpotX();
		_y = spot.getSpotY();
		Color spotColor;
		Color adjacentSpotColor;
		if (_next_to_play == Player.BLACK)
		{
			adjacentSpotColor = Color.WHITE;
			spotColor = Color.BLACK;
		}
		else
		{
			adjacentSpotColor = Color.BLACK;
			spotColor = Color.WHITE;
		}
		
		//test horizontally
		
		if (spot.getSpotColor() == Color.BLACK || spot.getSpotColor() == Color.WHITE)
		{
			return;
		}

		if ((_x-1 >= 0) && (_board.getSpotAt(_x-1, _y).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _x-1; i >= 0; i--)
			{
				if (_board.getSpotAt(i, _y).getSpotColor() == spotColor)
				{
					for (int j = i; j < _x; j++)
					{
						_board.getSpotAt(j, _y).setSpotColor(spotColor);
					}
					break;
				}
				
				else if (_board.getSpotAt(i, _y).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		else if ((_x+1 <= 7) && (_board.getSpotAt(_x+1, _y).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _x+1; i <= 7; i++)
			{
				if (_board.getSpotAt(i, _y).getSpotColor() == spotColor)
				{
					for (int j = i; j > _x; j--)
					{
						_board.getSpotAt(j, _y).setSpotColor(spotColor);
					}
					break;
				}
				
				else if (_board.getSpotAt(i, _y).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		//test vertically
		
		if ((_y-1 >= 0) && (_board.getSpotAt(_x, _y-1).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _y-1; i >= 0; i--)
			{
				if (_board.getSpotAt(_x, i).getSpotColor() == spotColor)
				{
					for (int j = i; j < _y; j++)
					{
						_board.getSpotAt(_x, j).setSpotColor(spotColor);
					}
					break;
				}
				
				else if (_board.getSpotAt(_x, i).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		else if ((_y+1 <= 7) && (_board.getSpotAt(_x, _y+1).getSpotColor() == adjacentSpotColor))
		{
			for (int i = _y+1; i <= 7; i++)
			{
				if (_board.getSpotAt(_x, i).getSpotColor() == spotColor)
				{
					for (int j = i; j > _y; j--)
					{
						_board.getSpotAt(_x, j).setSpotColor(spotColor);
					}
					break;
				}
				
				else if (_board.getSpotAt(_x, i).getSpotColor() == Color.LIGHT_GRAY)
				{
					break;
				}
			}
		}
		
		//test diagonally (bottom right)
		if ((_x+1 <= 7) && (_y+1 <= 7) &&_board.getSpotAt(_x+1, _y+1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x+i <= 7) && (_y+i <= 7) && (_board.getSpotAt(_x+i, _y+i).getSpotColor() == spotColor))
				{
					for (int j = i; j >= 0; j--)
					{
						_board.getSpotAt(_x+j, _y+j).setSpotColor(spotColor);
					}
					break;
				}
				
				else if ((_x+i <= 7) && (_y+i <= 7) && (_board.getSpotAt(_x+i, _y+i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		//test diagonally (top right)
		if ((_x+1 <= 7) && (_y-1 >= 0) && _board.getSpotAt(_x+1, _y-1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x+i <= 7) && (_y-i >= 0) && (_board.getSpotAt(_x+i, _y-i).getSpotColor() == spotColor))
				{
					for (int j = i; j >= 0; j--)
					{
						_board.getSpotAt(_x+j, _y-j).setSpotColor(spotColor);
					}
					break;
				}
				else if ((_x+i <= 7) && (_y-i >= 0) && (_board.getSpotAt(_x+i, _y-i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		//test diagonally (top left)
		if ((_x-1 >= 0) && (_y-1 >= 0) &&_board.getSpotAt(_x-1, _y-1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x-i >= 0) && (_y-i >= 0) && (_board.getSpotAt(_x-i, _y-i).getSpotColor() == spotColor))
				{
					for (int j = i; j >= 0; j--)
					{
						_board.getSpotAt(_x-j, _y-j).setSpotColor(spotColor);
					}
					break;
				}
				else if ((_x-i >= 0) && (_y-i >= 0) && (_board.getSpotAt(_x-i, _y-i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		if ((_x-1 >= 0) && (_y+1 <= 7) &&_board.getSpotAt(_x-1, _y+1).getSpotColor() == adjacentSpotColor)
		{
			for (int i = 1; i < 8; i++)
			{
				if ((_x-i >= 0) && (_y+i <= 7) && (_board.getSpotAt(_x-i, _y+i).getSpotColor() == spotColor))
				{
					for (int j = i; j >= 0; j--)
					{
						_board.getSpotAt(_x-j, _y+j).setSpotColor(spotColor);
					}
					break;
				}
				else if ((_x-i >= 0) && (_y+i <= 7) && (_board.getSpotAt(_x-i, _y+i).getSpotColor() == Color.LIGHT_GRAY))
				{
					break;
				}
			}
		}
		
		return;
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won)
		{
			return;
		}
		
		
		if (highlightSpot(spot))
		{
			spot.highlightSpot();
		}

	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		spot.unhighlightSpot();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		resetGame();
	}
}