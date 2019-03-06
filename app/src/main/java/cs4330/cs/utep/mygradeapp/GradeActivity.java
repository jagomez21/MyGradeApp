package cs4330.cs.utep.mygradeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        //ListView list = findViewById(R.id.gradeList);

        Intent i = getIntent();
        String username = i.getStringExtra("username");
        String password = i.getStringExtra("password");

        TextView information = findViewById(R.id.textView);

        WebClient web = new WebClient(new WebClient.GradeListener() {

            /** Called when a requested grade is found. */
            public void onGrade(String date, Grade grade) {
                List<Grade.Score> scores = grade.scores();

                ScoreListAdapter scoreList = new ScoreListAdapter(getApplicationContext(), scores);

                //list.setAdapter(scoreList);

                information.setText(Html.fromHtml(gradeReport(date, grade)));
            }

            /** Called when an error is encountered. */
            public void onError(String msg) {
                information.setText("Error: Invalid credentials, try again.");
            }
        });

        web.queryAsync(username, password);
    }

    private String gradeReport(String date, Grade grade) {
        String report = "";
        List<Grade.Score> scores = grade.scores();

        report += "<b>" + date + " <br/><br />"
                + "<b>Grade:</b> " + grade.total + " <br/>"
                + "<b>w-total:</b> " + grade.grade + " <br/>";

        for(Grade.Score score : scores) {
            report += "<b>" + score.name + "</b>: " + score.earned + " (max: " + score.max + ") <br/>";
        }

        return report;
    }

    private static class ScoreListAdapter extends ArrayAdapter<Grade.Score> {
        private final List<Grade.Score> scores;

        public ScoreListAdapter(Context ctx, List<Grade.Score> scores) {
            super (ctx, -1, scores);
            this.scores = scores;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView != null ? convertView : LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.grade_detail_row, parent, false);
            Grade.Score score = scores.get(position);
            /*TextView view = row.findViewById(R.id.gradeList);
            view.setText(score.name);
            view = row.findViewById(R.id.nameView);

            view.setText(score.max);
            view = row.findViewById(R.id.maxView);

            view.setText(score.earned);
            view = row.findViewById(R.id.earnedView);*/
            return row;
        }
    }
}
