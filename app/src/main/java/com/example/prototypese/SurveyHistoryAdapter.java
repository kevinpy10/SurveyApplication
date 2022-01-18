package com.example.prototypese;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class SurveyHistoryAdapter extends RecyclerView.Adapter<SurveyHistoryAdapter.ViewHolder> {

    Context ctx;
    Vector<SurveyHistory> surveyHistoryVector;
    SurveyDB surveyDB;
    Survey survey;

    public SurveyHistoryAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setSurveyHistoryVector(Vector<SurveyHistory> surveyHistoryVector) {
        this.surveyHistoryVector = surveyHistoryVector;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_survey_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        surveyDB = new SurveyDB(ctx);
        survey = surveyDB.getSurvey(surveyHistoryVector.get(position).getSurvey_id());

        holder.tv_survey_title.setText(survey.getSurvey_title());
        holder.tv_survey_author.setText("By : " + survey.getSurvey_author());
        holder.tv_filled_at.setText(surveyHistoryVector.get(position).getFilled_at());
        holder.tv_filled_reward.setText("Rp. " + surveyHistoryVector.get(position).getSurvey_filled_reward());

    }

    @Override
    public int getItemCount() {
        return surveyHistoryVector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_survey_title, tv_survey_author, tv_filled_at, tv_filled_reward;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_survey_title = itemView.findViewById(R.id.tv_survey_title);
            tv_survey_author = itemView.findViewById(R.id.tv_survey_author);
            tv_filled_at = itemView.findViewById(R.id.tv_filled_at);
            tv_filled_reward = itemView.findViewById(R.id.tv_filled_reward);
        }
    }
}
