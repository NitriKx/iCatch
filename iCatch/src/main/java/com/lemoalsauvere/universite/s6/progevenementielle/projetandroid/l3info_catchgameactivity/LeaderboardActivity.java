package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.*;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.LeaderboardController;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.LeaderboardModel;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LeaderboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        getFragmentManager().beginTransaction()
                .add(R.id.container_leaderboard_activity, new TableScoreFragment())
                .commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.leaderboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The score table fragment
     */
    public static class TableScoreFragment extends Fragment {

        public TableScoreFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

            TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.leaderboard_table_score);
            tableLayout.removeAllViewsInLayout();

            List<LeaderboardModel> leaderboardScores = LeaderboardController.getInstance().getLeaderboardScores();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

            int initialSizeForPriorityQueue = (leaderboardScores.size() > 0) ? leaderboardScores.size() : 1;
            PriorityQueue<LeaderboardModel> sortedScore = new PriorityQueue<LeaderboardModel>(initialSizeForPriorityQueue, new Comparator<LeaderboardModel>() {
                @Override
                public int compare(LeaderboardModel lhs, LeaderboardModel rhs) {
                    return ((Long) rhs.getScore()).compareTo((Long) lhs.getScore());
                }
            });
            sortedScore.addAll(leaderboardScores);

            TableRow.LayoutParams layoutParamsWithMatchParent = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT, 1.0f);

            TableRow.LayoutParams layoutParamsWithMatchParentAndWrap = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f);



            for(int i = 0; i < sortedScore.size(); i++) {

                LeaderboardModel score = sortedScore.poll();

                TableRow row = new TableRow(inflater.getContext());

                row.setLayoutParams(layoutParamsWithMatchParent);

                TextView playerName = new TextView(inflater.getContext());
                playerName.setText(score.getPlayerName());
                playerName.setGravity(View.TEXT_ALIGNMENT_CENTER);
                playerName.setLayoutParams(layoutParamsWithMatchParentAndWrap);
                playerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                TextView playerScore = new TextView(inflater.getContext());
                playerScore.setText(String.format("%d", score.getScore()));
                playerScore.setGravity(View.TEXT_ALIGNMENT_CENTER);
                playerScore.setLayoutParams(layoutParamsWithMatchParentAndWrap);
                playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                TextView scoreDate = new TextView(inflater.getContext());
                scoreDate.setText(dateFormat.format(score.getDate()));
                scoreDate.setGravity(View.TEXT_ALIGNMENT_CENTER);
                scoreDate.setLayoutParams(layoutParamsWithMatchParentAndWrap);
                scoreDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);


                row.addView(playerName, 0);
                row.addView(playerScore, 1);
                row.addView(scoreDate, 2);

                tableLayout.addView(row);
            }


            return rootView;
        }
    }

}
