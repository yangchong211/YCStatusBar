package com.yc.search;

import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class SuggestionsAdapter<S, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> implements Filterable {

    protected List<S> suggestions = new ArrayList<>();
    protected List<S> suggestions_clone = new ArrayList<>();
    private final LayoutInflater inflater;
    protected int maxSuggestionsCount = 5;

    public SuggestionsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addSuggestion(S r){
        if (maxSuggestionsCount <= 0)
            return;
        if (r == null)
            return;
        if (!suggestions.contains(r)) {
            if (suggestions.size() >= maxSuggestionsCount) {
                suggestions.remove(maxSuggestionsCount - 1);
            }
            suggestions.add(0, r);
        }else {
            suggestions.remove(r);
            suggestions.add(0, r);
        }
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void setSuggestions(List<S> suggestions) {
        this.suggestions = suggestions;
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void clearSuggestions() {
        suggestions.clear();
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void deleteSuggestion(int position, S r) {
        if(r == null){
            return;
        }
        if(suggestions.contains(r)) {
            this.notifyItemRemoved(position);
            suggestions.remove(r);
            suggestions_clone = suggestions;
        }
    }

    public List<S> getSuggestions() {
        return suggestions;
    }

    public int getMaxSuggestionsCount() {
        return maxSuggestionsCount;
    }

    public void setMaxSuggestionsCount(int maxSuggestionsCount) {
        this.maxSuggestionsCount = maxSuggestionsCount;
    }

    protected LayoutInflater getLayoutInflater(){
        return this.inflater;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        onBindSuggestionHolder(suggestions.get(position), holder, position);
    }

    public abstract void onBindSuggestionHolder(S suggestion, V holder, int position);

    public abstract int getSingleViewHeight();

    public int getListHeight(){
        return getItemCount() * getSingleViewHeight();
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

}
