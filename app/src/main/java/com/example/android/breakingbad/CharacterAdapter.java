package com.example.android.breakingbad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private final String TAG = CharacterAdapter.class.getSimpleName();
    private ArrayList<Character> characterList;
    private final CharacterOnClickHandler mCharacterClickHandler;

    public CharacterAdapter(ArrayList<Character> characterList, CharacterOnClickHandler clickHandler) {
        Log.d(TAG, "CharacterAdapter: constructor called");
        this.characterList = characterList;
        this.mCharacterClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        Context context = parent.getContext();
        //int layoutIdForListItem = R.layout.character_list_item;
        int layoutIdForListItem = R.layout.character_list_item2;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        Character character = characterList.get(position);
        Log.i(TAG, "onBindViewHolder: character is " + character.toString());
        holder.mCharacterNameTextView.append(" " + character.getName());
        holder.mCharacterStatusTextView.append(" " + character.getStatus());
        holder.mCharacterNicknameTextView.append(" " + character.getNickName());
        Picasso.get().load(character.getImage()).into(holder.mCharacterImageView);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mCharacterNameTextView;
        public final ImageView mCharacterImageView;
        public final TextView mCharacterNicknameTextView;
        public final TextView mCharacterStatusTextView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "CharacterViewHolder() called with: itemView = [" + itemView + "]");
//            mCharacterNameTextView = itemView.findViewById(R.id.tv_character_item_name);
//            mCharacterImageView = itemView.findViewById(R.id.iv_character_item_image);
//            mCharacterNicknameTextView = itemView.findViewById(R.id.tv_character_item_nickname);
//            mCharacterStatusTextView = itemView.findViewById(R.id.tv_character_item_status);
            mCharacterNameTextView = itemView.findViewById(R.id.name2);
            mCharacterImageView = itemView.findViewById(R.id.imageView);
            mCharacterNicknameTextView = itemView.findViewById(R.id.nickname2);
            mCharacterStatusTextView = itemView.findViewById(R.id.status2);

            mCharacterNicknameTextView.setOnClickListener(this);
            mCharacterNameTextView.setOnClickListener(this);
            mCharacterImageView.setOnClickListener(this);
            mCharacterStatusTextView.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick() called with: view = [" + view + "]");
            int itemIndex = getAdapterPosition();
            Log.i(TAG, "onClick: item index is " + itemIndex);
            mCharacterClickHandler.onCharacterClick(view, itemIndex);
        }
    }
}
