package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {
	
	private enum Player {WHITE, BLACK};
	
	private JSpotBoard _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	private Color _winner_color;
	private boolean _is_draw;
	
	public TicTacToeWidget()
	{
		_board = new JSpotBoard(3,3);
		for (Spot s : _board)
		{
			s.setBackground(Color.LIGHT_GRAY);
			s.setSpotColor(Color.LIGHT_GRAY);
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
		for (Spot s : _board)
		{
			s.clearSpot();
			s.setSpotColor(Color.LIGHT_GRAY);
			_game_won = false;
			_is_draw = false;
			_next_to_play = Player.WHITE;
			_message.setText("Welcome to Tic Tac Toe. White shall go first!");
	
		}
	}
	
	private boolean checkWinner(Spot spot)
	{
		//Checks if we won horizontally
		for (int i = 0; i < _board.getSpotHeight(); i++)
		{
			if ((spot.getSpotColor().equals(_board.getSpotAt(0, i).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(1,i).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(2, i).getSpotColor())))
			{
				_game_won = true;
				_winner_color = _board.getSpotAt(0, i).getSpotColor();
				return true;
			}
		}
				
		//Checks if we won vertically
		for (int i = 0; i < _board.getSpotWidth(); i++)
		{
			if ((spot.getSpotColor().equals(_board.getSpotAt(i, 0).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(i,1).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(i, 2).getSpotColor())))
			{
				_game_won = true;
				_winner_color = _board.getSpotAt(i, 0).getSpotColor();
				return true;
			}
		}
				
		//Checks if we won diagonally
		if ((spot.getSpotColor().equals(_board.getSpotAt(0, 0).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(1,1).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(2, 2).getSpotColor())))
		{
			
			_game_won = true;
			_winner_color = _board.getSpotAt(1, 1).getSpotColor();
			return true;
		}
				
		//Checks if we won diagonally		
		if ((spot.getSpotColor().equals(_board.getSpotAt(0, 2).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(1,1).getSpotColor())) && (spot.getSpotColor().equals(_board.getSpotAt(2, 0).getSpotColor())))
		{
			_game_won = true;
			_winner_color = _board.getSpotAt(1, 1).getSpotColor();
			return true;

		}
		
		return false;
	}
	
	
	private boolean checkDraw(Spot spot)
	{
		//There's a draw
		if (!checkWinner(spot) && fullBoard())
		{
			_is_draw = true;
			return true;
		}
		//No draw but also no winner
		else if (!checkWinner(spot) && !fullBoard())
		{
			_is_draw = false;
			return false;
		}
		
		//There's a winner
		else
		{
			_is_draw = false;
			return false;
		}
	}
	
	private boolean fullBoard()
	{
		int i = 0;
		for (Spot s : _board)
		{
			if (s.getSpotColor().equals(Color.BLACK) || s.getSpotColor().equals(Color.WHITE))
			{
				i++;
			}
		}
		
		if (i == 9)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	@Override
	public void spotClicked(Spot spot) {
		
		if (_game_won)
		{
			return;
		}
		
		if (_is_draw)
		{
			return;
		}
		
		if (spot.getSpotColor() == Color.WHITE || spot.getSpotColor() == Color.BLACK)
		{
			return;
		}

		String next_player_name = null;
		Color player_color = null;
		
		
		if (_next_to_play == Player.BLACK)
		{
			player_color = Color.BLACK;
			next_player_name = "White";
			_next_to_play = Player.WHITE;
		}
		else
		{
			player_color = Color.WHITE;

			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		}
		
		spot.setSpotColor(player_color);
		spot.toggleSpot();
		spot.toggleHighlight();
	

		checkDraw(spot);
		checkWinner(spot);
		
		//Messages
		if (_game_won)
		{
			if (_winner_color == Color.BLACK)
			{
				_message.setText("Black wins!");
			}
			
			else 
			{
				_message.setText("White wins!");
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
		if (_game_won) {
			return;
		}
		
		if (_is_draw)
		{
			return;
		}
		
		if (spot.getSpotColor().equals(Color.BLACK) || spot.getSpotColor().equals(Color.WHITE))
		{
			return;
		}
		spot.highlightSpot();
		
	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();
		
	}
	
	
}
