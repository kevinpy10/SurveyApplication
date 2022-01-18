package com.example.prototypese;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class YourSurveyAdapter extends RecyclerView.Adapter<YourSurveyAdapter.ViewHolder> {

    Context ctx;
    Vector<Survey> yourSurveyVector;

    public YourSurveyAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setYourSurveyVector(Vector<Survey> yourSurveyVector) {
        this.yourSurveyVector = yourSurveyVector;
    }

    @NonNull
    @Override
    public YourSurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_your_survey, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourSurveyAdapter.ViewHolder holder, int position) {
        holder.tv_survey_title.setText(yourSurveyVector.get(position).getSurvey_title());
        holder.tv_created_at.setText(yourSurveyVector.get(position).getCreated_at());
        holder.tv_participant.setText(yourSurveyVector.get(position).getSurvey_participant() + "/" + yourSurveyVector.get(position).getSurvey_max_participant());

        holder.cv_your_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, SurveyDetailActivity.class);
                intent.putExtra("type", "your_survey");
                intent.putExtra("category", yourSurveyVector.get(position).getSurvey_category());
                intent.putExtra("title", yourSurveyVector.get(position).getSurvey_title());
                intent.putExtra("desc", yourSurveyVector.get(position).getSurvey_desc());
                intent.putExtra("reward", yourSurveyVector.get(position).getSurvey_reward());
                intent.putExtra("participant", yourSurveyVector.get(position).getSurvey_participant());
                intent.putExtra("max_participant", yourSurveyVector.get(position).getSurvey_max_participant());
                intent.putExtra("author", yourSurveyVector.get(position).getSurvey_author());
                intent.putExtra("url", yourSurveyVector.get(position).getSurvey_url());
                intent.putExtra("user_id", yourSurveyVector.get(position).getUserId());
                intent.putExtra("survey_id", yourSurveyVector.get(position).getSurvey_id());

                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yourSurveyVector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_survey_title, tv_created_at, tv_participant;
        CardView cv_your_survey;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_survey_title = itemView.findViewById(R.id.tv_survey_title);
            tv_created_at = itemView.findViewById(R.id.tv_created_at);
            tv_participant = itemView.findViewById(R.id.tv_participant);
            cv_your_survey = itemView.findViewById(R.id.cv_your_survey);
        }
    }
}
