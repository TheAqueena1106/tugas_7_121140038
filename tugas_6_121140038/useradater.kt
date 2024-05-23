class useradater {
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import kotlinx.android.synthetic.main.item_user.view.*

    class UserAdapter(private val users: List<User>) :
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        inner class UserViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            fun bind(user: User) {
                itemView.txtName.text = "${user.firstName} ${user.lastName}"
                itemView.txtEmail.text = user.email
                Glide.with(itemView)
                    .load(user.avatar)
                    .into(itemView.imgAvatar)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int)
        {
            holder.bind(users[position])
        }

        override fun getItemCount(): Int {
            return users.size
        }
    }

        }