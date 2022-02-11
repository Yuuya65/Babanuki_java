package pac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Babanuki {
	/************************************************************************
	 * ���C���v���O����
	 ************************************************************************/
	public static void main(String[] args) throws IOException {
		seiri s = new seiri();
		ranking r = new ranking();
		take_taken t = new take_taken();
		game g = new game();
		
		int[] player_nokori_card;
		String[][] player_card;
		int totalplayer = 0;
		
		System.out.println("\n�`�`�`�`�`�`�`�`�`�`�`�`�o�o�����v���O�����`�`�`�`�`�`�`�`�`�`�`�`\n");
		String print1 = "�v���C���[�l������͂��Ă�������>>>";
		do {
			totalplayer = Enter_number.main(print1);
			if(totalplayer == 1) {
				System.out.println("1�l�Ńo�o��������́H�i���΁j���O���݂����z��ȁi�o�΁j");
			}
		}while(totalplayer<2);
				
		//�J�[�h�z�z
		System.out.println("\n�J�[�h��z�z���܂�");
		make_card mc = new make_card(totalplayer);
		player_card = mc.player_card;
		player_nokori_card = mc.player_nokori_card;
		on_display.method(player_card, player_nokori_card, 0);
		
		//�J�[�h����
		System.out.println("\n���������J�[�h");
		for(int a = 0;a < totalplayer;a++) {
			seiri seiri = s.method(totalplayer, player_card, player_nokori_card, a);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
		}
		on_display.method(player_card,player_nokori_card,0);
		
		//�J�[�h���������Ă���v���C���[�̕\��
		int[] syoji_player = new int[totalplayer];
		for(int i = 0;i < syoji_player.length;i++) {
			syoji_player[i] = i;	//�Q�[�����甲�����v���C���[�̗v�f�ɂ�-1������
		}
		
		//�Z�������L�^����z��
		int[] array_rank = new int[totalplayer];
		for(int i= 0;i<array_rank.length;i++) {
			array_rank[i] = -1;		//�����l��-1
		}
		int rank = 0;
		for(int i = 0;i < totalplayer;i++) {
			ranking ranking = r.method(player_nokori_card, i, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
		}
		
		//�J�[�h�����v���C���[�Ǝ����v���C���[�̌���
		take_taken take_taken = t.method(syoji_player, 0);	//�ŏ���take��0������
		int take = take_taken.take;
		int taken = take_taken.taken;
		
		//�Q�[��������
		System.out.println("\n-----------------------�Q�[���J�n�I-----------------------");
		game game = g.method(totalplayer,player_card, player_nokori_card,syoji_player, array_rank, rank, take, taken);
		array_rank = game.array_rank;
		rank = game.rank;
		
		//���ʔ��\
		result(array_rank, rank);
	}
	/******************************************************************************
	 * ���ʂ�\������v���O����
	 ******************************************************************************/
	public static void result(int[] array_rank,int rank) {
		System.out.println("------------------------���ʔ��\------------------------");
		for(int i = 0;i < array_rank.length-1;i++) {
			System.out.println((i+1)+"������ player"+(array_rank[i]+1));
		}
		System.out.println("\n�o�o�� player"+(array_rank[rank]+1));
		System.out.println("-----------------------�܂��V��ł�-----------------------");
	}
}
class on_display{
	/***************************************************************************
	 * �w�肵���v���C���[�̃J�[�h��\������v���O����
	 * 
	 * �w�p�����[�^�[�̍Ō�ɂ́A�\���������v���C���[�̔ԍ�-1 or �S���\����-10����́x
	 ***************************************************************************/
	public static void method(String[][] player_card,int[] player_nokori_card,int num) {
		if(num == -10) {
			for(int i = 0;i < player_card.length;i++) {
				System.out.print("player "+ (i + 1) + "�̃J�[�h\t�w");
				for(int j = 0;j < player_nokori_card[i];j++) {
					if((j + 1) ==  player_nokori_card[i]){
						System.out.println(player_card[i][j] + "�x\t");
					}else{
						System.out.print(player_card[i][j]+",");
					}
				}
				System.out.print("�c��@���@�s" + player_nokori_card[i] + "���t\n");
			}
		}else {
			if(player_nokori_card[num] == 0) {
				System.out.print("player"+ (num + 1) + "�̃J�[�h��");
			}else {
				System.out.print("player"+ (num + 1) + "�̃J�[�h\t�w");
			}
			for(int j = 0;j < player_nokori_card[num];j++) {
				if((j + 1) ==  player_nokori_card[num]){
					System.out.println(player_card[num][j] + "�x\t");
				}else{
					System.out.print(player_card[num][j]+",");
				}
			}
			System.out.print("�c�薇�����@�s" + player_nokori_card[num] +"���t\n");
		}
	}
}
/*****************************************************************************
 * �J�[�h���쐬����v���O����
 *****************************************************************************/
class make_card{
	int[] player_nokori_card;
	String[][] player_card;
	public make_card(int totalplayer) {
		String[] card = {"Joker",																							//Joker
				"Clu01","Clu02","Clu03","Clu04","Clu05","Clu06","Clu07","Clu08","Clu09","Clu10","Clu11","Clu12","Clu13",	//Clubs�@�@�@
				"Dia01","Dia02","Dia03","Dia04","Dia05","Dia06","Dia07","Dia08","Dia09","Dia10","Dia11","Dia12","Dia13",	//Diamonds
				"Hea01","Hea02","Hea03","Hea04","Hea05","Hea06","Hea07","Hea08","Hea09","Hea10","Hea11","Hea12","Hea13",	//Heart
				"Spa01","Spa02","Spa03","Spa04","Spa05","Spa06","Spa07","Spa08","Spa09","Spa10","Spa11","Spa12","Spa13",	//Spade
		};
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0 ; i < 53 ; i++) {
			list.add(i);
        }		
		Collections.shuffle(list);
		int[] card_num = new int[53];
		for (int i =0;i<card_num.length;i++) {
			card_num[i] = list.get(i);
		}
		int cardamari = 53%totalplayer;
		this.player_nokori_card = new int[totalplayer];
		for (int i = 0;i < player_nokori_card.length;i++) {
			if(i<cardamari) {
				player_nokori_card[i] = 53/totalplayer+1;
			}else {
				player_nokori_card[i] = 53/totalplayer;
			}
		}
		this.player_card = new String[totalplayer][53/totalplayer+3];
		//�S�Ă̗v�f�ɏ����l�uXXXno�v������
		for(int i = 0;i < player_card.length;i++) {
			for(int j = 0;j < player_card[i].length;j++) {
				this.player_card[i][j] = "XXXno";
			}
		}
		//�e�v�f�ɃJ�[�h������
		int cardfn = 0;
		for(int i = 0;i < player_card.length;i++) {
			for(int j = 0;j < player_nokori_card[i];j++) {
				this.player_card[i][j] = card[card_num[cardfn]];
				cardfn++;
			}
		}
	}
}
/******************************************************************************
 * �J�[�h�𐮗�����v���O����
 ******************************************************************************/
class seiri{
	int[] player_n_c;
	String[][] player_c;
	/*********************************************************************************
	 * ���������̃J�[�h����������v���O����
	 *********************************************************************************/
	public seiri method(int totalplayer,String[][] player_num_card, int[] player_num_nokori_card, int num) {
		seiri output = new seiri();
		
		//�񎟌��z�񂩂�ꎟ���z��ɂ���
		String[] compare = new String[player_num_card[num].length];
		String[] replace = new String[compare.length];
		//�J�[�h�̐����������c��
		for(int i = 0;i < compare.length;i++) {
			compare[i] = player_num_card[num][i].substring(3,5);
		}
		//���������̃J�[�h�������ď�������
		for(int i = 0;i < compare.length-1;i++) {
			int j = i + 1;
			for(;j < compare.length;j++) {
				if ((compare[i]).equals(compare[j])) {
					compare[i] = "no";
					compare[j] = "no";
				}
			}
		}
		//�J�[�h�Ƀ}�[�N��t���߂�
		for(int i = 0;i < compare.length;i++) {
			if(compare[i].equals("no")) {
				replace[i] = "XXXno";
			}else {
				replace[i] = player_num_card[num][i].substring(0,3)+compare[i];
			}
		}
		//�z��player_num_card�̑S�Ă̗v�f�ɁuXXXno�v������
		for(int i = 0;i < player_num_card[num].length;i++) {
			player_num_card[num][i] = "XXXno";
		}
		//�z��replace�̗v�f�ɁuXXXno�v�������Ă���v�f�����Ȃ�
		int idx0 = 0;
		for(int idx1 = 0;idx1 < player_num_card[num].length;idx1++) {
			if(!(replace[idx1].equals("XXXno"))) {
				player_num_card[num][idx0] = replace[idx1];
				idx0++;
			}
		}
		player_num_nokori_card[num] = idx0;
		output.player_c = player_num_card;
		output.player_n_c = player_num_nokori_card;
		return output;
	}
}
class ranking{
	int[] syoji_player;
	int[] array_rank;
	int rank;
	/**************************************************************************
	 * �v���C���[���Q�[�����甲�������𔻒肷��v���O����
	 * �wnum�̓v���C���[�ԍ�����́x
	 **************************************************************************/
	public ranking method(int[] player_nokori_card, int num,int[] syoji_player,  int[] array_rank, int rank) {
		ranking output = new ranking();
		if(player_nokori_card[num] == 0) {
			System.out.println("�������������� �v���C���["+(num+1)+"�������܂���!! ��������������");
			syoji_player[num] = -1;
			array_rank[rank] = num;
			rank++;
		}
		output.syoji_player = syoji_player;
		output.array_rank = array_rank;
		output.rank = rank;
		return output;
	}
}
class take_taken{
	int take = 0;
	int taken = 0;
	/***************************************************************************
	 * �J�[�h�����v���C���[�Ǝ����v���C���[�����߂�v���O����
	 ***************************************************************************/
	public take_taken method(int[] syoji_player,int take) {
		take_taken output = new take_taken();
		
		String redo ="redo";
		do {
			if(syoji_player[take] != -1) {				//�v���C���[�����݂���Ƃ�
				redo = null;
			}else if(take == syoji_player.length-1) {	//�v���C���[�����݂����v���C���[�ԍ����Ō�̂Ƃ�
				take = 0;
			}else {										//�v���C���[�����݂��Ȃ��Ƃ�
				take++;
			}
		}while(redo == "redo");
		
		taken = syoji_player[take]+1;
		redo = "redo";
		do {
			if(taken >= syoji_player.length) {
				taken = 0;
			}
			if(syoji_player[taken] != -1) {				//�v���C���[�����݂���Ƃ�
				redo = null;
			}else if(taken == syoji_player.length-1) {	//�v���C���[�����݂����v���C���[�ԍ����Ō�̂Ƃ�
				taken = 0;
			}else {										//�v���C���[�����݂��Ȃ��Ƃ�
				taken++;
			}
		}while(redo == "redo");
		
		output.take = syoji_player[take];
		output.taken = syoji_player[taken];
		return output;
	}
}
class game{
	int[] array_rank;
	int rank;
	/******************************************************************************
	 * �Q�[��������v���O����
	 ******************************************************************************/
	public game method(int totalplayer,String[][]player_card,int[] player_nokori_card,int[] syoji_player,int[] array_rank,int rank,int take,int taken) throws IOException {
		seiri s = new seiri();
		ranking r = new ranking();
		take_taken t = new take_taken();
		
		game output = new game();
		
		int[] array_take_card = {0,1,2,3,4,5,6,7,8,9,10};
		int take_number = 0;
		int mae = 0;	int ato = 0;			//�J�[�h���������v���C���[�̎c���D����������������
		int count = 1;
		do {
			//System.out.println("�J�E���g�@�k"+count+"�l");
			mae = player_nokori_card[take];
			//take��taken�̃J�[�h������
			if(take == 0) {
				on_display.method(player_card,player_nokori_card,take);
				//���̃v���C���[�̃J�[�h��
				for(int i = 1;i < player_nokori_card.length;i++) {
					System.out.println("player"+(i+1)+"�̎c��J�[�h���s"+player_nokori_card[i]+"���t ");
				}	
				String print = ("player"+(taken+1)+"����J�[�h�������B\n�s1�`"+player_nokori_card[taken]+"�t�@�̒����琔����I�ԁB>>>");
				do {
					take_number = Enter_number.main(print)-1;
				}while(take_number >= player_nokori_card[taken] || take_number < 0);
				System.out.println("�������J�[�h�́@�w"+player_card[taken][take_number]+"�x");
			}else {
				do {
					Random r1 = new Random();
					int ran = array_take_card[r1.nextInt(player_nokori_card[taken])];
					take_number = ran;
				}while(take_number >= player_nokori_card[taken] || take_number < 0);
				System.out.println("�v���C���["+(take+1)+"���A�v���C���["+(taken+1)+"����J�[�h�������܂����B");
			}
			
			//take�ɃJ�[�h��ǉ�����
			player_card[take][player_nokori_card[take]] = player_card[taken][take_number];
			player_nokori_card[take]++;
			seiri seiri = s.method(totalplayer, player_card, player_nokori_card, take);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
			
			ato = player_nokori_card[take];
			
			//�v���C���[��taken�̏ꍇ
			if(taken == 0) {
				System.out.println("player"+(take+1)+"��player1�̃J�[�h����w"+player_card[taken][take_number]+"�x�������܂����B");
			}
			
			//��D����v���������m�F����
			if(mae>ato) {
				System.out.println("-----------------------�J�[�h����v-----------------------\n");
			}else {
				System.out.println("-----------------------�J�[�h�s��v-----------------------\n");
			}
			if(take == 0) {
				on_display.method(player_card,player_nokori_card,take);
			}
			//take�������������肷��
			ranking ranking = r.method(player_nokori_card, take, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
						
			//taken�̃J�[�h����I�΂ꂽtake_number���폜����
			player_card[taken][take_number] = "XXXno";
			seiri = s.method(totalplayer, player_card, player_nokori_card, taken);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
			
			//taken�������������肷��
			ranking = r.method(player_nokori_card, taken, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
			
			//����take��taken�����߂�
			take = taken;
			take_taken take_teken = t.method(syoji_player, take);
			take = take_teken.take;
			taken = take_teken.taken;
			
			System.out.println("�c��v���C�l���s"+(totalplayer-rank)+"�l�t");
			count++;
		}while(rank != (totalplayer-1));
		
		for(int i = 0;i<totalplayer;i++) {			//�o�o��array_rank�ɓ����
			if(syoji_player[i] != -1) {
				array_rank[rank] = i;
			}
		}
		System.out.println();
		
		output.array_rank = array_rank;
		output.rank = rank;
		return output;
	}
}
/*****************************************************************************
 * ��������͂���v���O����
 *****************************************************************************/
class Enter_number{
	public static int main (String print) throws IOException {
		int number = 0;
		String numberS = null;
		String error = null;
		
		System.out.print(print);
		do {
			BufferedReader br =
					new BufferedReader(new InputStreamReader(System.in));
		    numberS = br.readLine();
		    try {
		    	number = Integer.parseInt(numberS);
		    	error = null;
		    }catch(NumberFormatException e) {
		    	System.out.println("�������l����͂ł��Ă��܂���B");
		    	error = "redo";
		    }
		}while(error == "redo");
		return number;
	}
}