package com.example.prototypese;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class SearchSurveyAdapter extends RecyclerView.Adapter<SearchSurveyAdapter.ViewHolder> {

    Context ctx;
    int userId;
    Vector<Survey> surveyVector;
    Vector<Survey> surveyVectorCopy;
    SurveyHistoryDB surveyHistoryDB;

    public SearchSurveyAdapter(Context ctx, int userId) {
        this.ctx = ctx;
        this.userId = userId;
    }

    public void setSurveyVector(Vector<Survey> surveyVector) {
        this.surveyVector = surveyVector;
        surveyVectorCopy = new Vector<>();
        surveyVectorCopy.addAll(surveyVector);
    }

    @NonNull
    @Override
    public SearchSurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_survey, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSurveyAdapter.ViewHolder holder, int position) {
        surveyHistoryDB = new SurveyHistoryDB(ctx);

        holder.tv_survey_title.setText(surveyVector.get(position).getSurvey_title());
        holder.tv_survey_author.setText("By : " + surveyVector.get(position).getSurvey_author());
        holder.tv_participant.setText(surveyVector.get(position).getSurvey_participant() + "/" + surveyVector.get(position).getSurvey_max_participant());
        holder.tv_reward.setText("Rp. " + String.valueOf(surveyVector.get(position).getSurvey_reward() / surveyVector.get(position).getSurvey_max_participant()));

        holder.cv_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (surveyHistoryDB.checkSurvey(userId, surveyVector.get(position).getSurvey_id())) {
                    Toast.makeText(ctx, "You have already filled this survey!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(ctx, SurveyDetailActivity.class);
                    intent.putExtra("type", "survey");
                    intent.putExtra("category", surveyVector.get(position).getSurvey_category());
                    intent.putExtra("title", surveyVector.get(position).getSurvey_title());
                    intent.putExtra("desc", surveyVector.get(position).getSurvey_desc());
                    intent.putExtra("reward", surveyVector.get(position).getSurvey_reward());
                    intent.putExtra("participant", surveyVector.get(position).getSurvey_participant());
                    intent.putExtra("max_participant", surveyVector.get(position).getSurvey_max_participant());
                    intent.putExtra("author", surveyVector.get(position).getSurvey_author());
                    intent.putExtra("url", surveyVector.get(position).getSurvey_url());
                    intent.putExtra("user_id", userId);
                    intent.putExtra("survey_id", surveyVector.get(position).getSurvey_id());

                    ctx.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveyVector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_survey_title, tv_survey_author, tv_participant, tv_reward;
        CardView cv_survey;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_survey_title = itemView.findViewById(R.id.tv_survey_title);
            tv_survey_author = itemView.findViewById(R.id.tv_survey_author);
            tv_participant = itemView.findViewById(R.id.tv_participant);
            tv_reward = itemView.findViewById(R.id.tv_reward);
            cv_survey = itemView.findViewById(R.id.cv_survey);
        }
    }

    public void filter(CharSequence s) {
        Vector<Survey> tempVector = new Vector<>();
        Vector<Survey> allSurveys = new Vector<>();

        if(!TextUtils.isEmpty(s)) {
            SurveyDB surveyDB = new SurveyDB(ctx);
            allSurveys = surveyDB.getAllSurvey();

            for (Survey survey : allSurveys){
                if(survey.getSurvey_title().toLowerCase().contains(s)){
                    tempVector.add(survey);
                }
            }
        }else {
            tempVector.addAll(surveyVectorCopy);
        }

        surveyVector.clear();
        surveyVector.addAll(tempVector);
        notifyDataSetChanged();
        tempVector.clear();
    }
}
