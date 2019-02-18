package me.camdenorrb.purrbot.impl

import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.minibus.listener.MiniListener
import net.dv8tion.jda.core.events.*
import net.dv8tion.jda.core.events.channel.category.CategoryCreateEvent
import net.dv8tion.jda.core.events.channel.category.CategoryDeleteEvent
import net.dv8tion.jda.core.events.channel.category.GenericCategoryEvent
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdateNameEvent
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdatePermissionsEvent
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdatePositionEvent
import net.dv8tion.jda.core.events.channel.category.update.GenericCategoryUpdateEvent
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelCreateEvent
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelDeleteEvent
import net.dv8tion.jda.core.events.channel.text.GenericTextChannelEvent
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent
import net.dv8tion.jda.core.events.channel.text.update.*
import net.dv8tion.jda.core.events.channel.voice.GenericVoiceChannelEvent
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelDeleteEvent
import net.dv8tion.jda.core.events.channel.voice.update.*
import net.dv8tion.jda.core.events.emote.EmoteAddedEvent
import net.dv8tion.jda.core.events.emote.EmoteRemovedEvent
import net.dv8tion.jda.core.events.emote.GenericEmoteEvent
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateNameEvent
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateRolesEvent
import net.dv8tion.jda.core.events.emote.update.GenericEmoteUpdateEvent
import net.dv8tion.jda.core.events.guild.*
import net.dv8tion.jda.core.events.guild.member.*
import net.dv8tion.jda.core.events.guild.update.*
import net.dv8tion.jda.core.events.guild.voice.*
import net.dv8tion.jda.core.events.http.HttpRequestEvent
import net.dv8tion.jda.core.events.message.*
import net.dv8tion.jda.core.events.message.guild.*
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveAllEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent
import net.dv8tion.jda.core.events.message.priv.*
import net.dv8tion.jda.core.events.message.priv.react.GenericPrivateMessageReactionEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveAllEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.core.events.role.GenericRoleEvent
import net.dv8tion.jda.core.events.role.RoleCreateEvent
import net.dv8tion.jda.core.events.role.RoleDeleteEvent
import net.dv8tion.jda.core.events.role.update.*
import net.dv8tion.jda.core.events.self.*
import net.dv8tion.jda.core.events.user.GenericUserEvent
import net.dv8tion.jda.core.events.user.UserTypingEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateAvatarEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateDiscriminatorEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateNameEvent
import net.dv8tion.jda.core.events.user.update.UserUpdateOnlineStatusEvent
import net.dv8tion.jda.core.hooks.IEventManager

class MiniEventManager(val miniBus: MiniBus) : IEventManager {

    override fun register(listener: Any?) {

        check(listener is MiniListener) {
            "You tried to register a class that isn't implementing MiniListener"
        }

        miniBus.register(listener)
    }

    override fun unregister(listener: Any?) {

        check(listener is MiniListener) {
            "You tried to unregister a class that isn't implementing MiniListener"
        }

        miniBus.unregister(listener)
    }

    override fun getRegisteredListeners(): List<Any> {
        return miniBus.listenerTable.map.toList()
    }

    override fun handle(event: Event) {
        when (event) {
            is UpdateEvent<*, *> -> miniBus(event)
            is ReadyEvent -> miniBus(event)
            is ResumedEvent -> miniBus(event)
            is ReconnectedEvent -> miniBus(event)
            is DisconnectEvent -> miniBus(event)
            is ShutdownEvent -> miniBus(event)
            is StatusChangeEvent -> miniBus(event)
            is ExceptionEvent -> miniBus(event)
            is GuildMessageReceivedEvent -> miniBus(event)
            is GuildMessageUpdateEvent -> miniBus(event)
            is GuildMessageDeleteEvent -> miniBus(event)
            is GuildMessageEmbedEvent -> miniBus(event)
            is GuildMessageReactionAddEvent -> miniBus(event)
            is GuildMessageReactionRemoveEvent -> miniBus(event)
            is GuildMessageReactionRemoveAllEvent -> miniBus(event)
            is PrivateMessageReceivedEvent -> miniBus(event)
            is PrivateMessageUpdateEvent -> miniBus(event)
            is PrivateMessageDeleteEvent -> miniBus(event)
            is PrivateMessageEmbedEvent -> miniBus(event)
            is PrivateMessageReactionAddEvent -> miniBus(event)
            is PrivateMessageReactionRemoveEvent -> miniBus(event)
            is MessageReceivedEvent -> miniBus(event)
            is MessageUpdateEvent -> miniBus(event)
            is MessageDeleteEvent -> miniBus(event)
            is MessageBulkDeleteEvent -> miniBus(event)
            is MessageEmbedEvent -> miniBus(event)
            is MessageReactionAddEvent -> miniBus(event)
            is MessageReactionRemoveEvent -> miniBus(event)
            is MessageReactionRemoveAllEvent -> miniBus(event)
            is UserUpdateNameEvent -> miniBus(event)
            is UserUpdateDiscriminatorEvent -> miniBus(event)
            is UserUpdateAvatarEvent -> miniBus(event)
            //is UserUpdateActivityOrderEvent -> miniBus(event)
            is UserUpdateOnlineStatusEvent -> miniBus(event)
            is UserTypingEvent -> miniBus(event)
            //is UserActivityStartEvent -> miniBus(event)
            //is UserActivityEndEvent -> miniBus(event)
            is SelfUpdateAvatarEvent -> miniBus(event)
            is SelfUpdateEmailEvent -> miniBus(event)
            is SelfUpdateMFAEvent -> miniBus(event)
            is SelfUpdateNameEvent -> miniBus(event)
            is SelfUpdateVerifiedEvent -> miniBus(event)
            is TextChannelCreateEvent -> miniBus(event)
            is TextChannelUpdateNameEvent -> miniBus(event)
            is TextChannelUpdateTopicEvent -> miniBus(event)
            is TextChannelUpdatePositionEvent -> miniBus(event)
            is TextChannelUpdatePermissionsEvent -> miniBus(event)
            is TextChannelUpdateNSFWEvent -> miniBus(event)
            is TextChannelUpdateParentEvent -> miniBus(event)
            is TextChannelUpdateSlowmodeEvent -> miniBus(event)
            is TextChannelDeleteEvent -> miniBus(event)
            is VoiceChannelCreateEvent -> miniBus(event)
            is VoiceChannelUpdateNameEvent -> miniBus(event)
            is VoiceChannelUpdatePositionEvent -> miniBus(event)
            is VoiceChannelUpdateUserLimitEvent -> miniBus(event)
            is VoiceChannelUpdateBitrateEvent -> miniBus(event)
            is VoiceChannelUpdatePermissionsEvent -> miniBus(event)
            is VoiceChannelUpdateParentEvent -> miniBus(event)
            is VoiceChannelDeleteEvent -> miniBus(event)
            is CategoryCreateEvent -> miniBus(event)
            is CategoryUpdateNameEvent -> miniBus(event)
            is CategoryUpdatePositionEvent -> miniBus(event)
            is CategoryUpdatePermissionsEvent -> miniBus(event)
            is CategoryDeleteEvent -> miniBus(event)
            is PrivateChannelCreateEvent -> miniBus(event)
            is PrivateChannelDeleteEvent -> miniBus(event)
            is GuildReadyEvent -> miniBus(event)
            is GuildJoinEvent -> miniBus(event)
            is GuildLeaveEvent -> miniBus(event)
            is GuildAvailableEvent -> miniBus(event)
            is GuildUnavailableEvent -> miniBus(event)
            is UnavailableGuildJoinedEvent -> miniBus(event)
            is GuildBanEvent -> miniBus(event)
            is GuildUnbanEvent -> miniBus(event)
            is GuildUpdateAfkChannelEvent -> miniBus(event)
            is GuildUpdateSystemChannelEvent -> miniBus(event)
            is GuildUpdateAfkTimeoutEvent -> miniBus(event)
            is GuildUpdateExplicitContentLevelEvent -> miniBus(event)
            is GuildUpdateIconEvent -> miniBus(event)
            is GuildUpdateMFALevelEvent -> miniBus(event)
            is GuildUpdateNameEvent -> miniBus(event)
            is GuildUpdateNotificationLevelEvent -> miniBus(event)
            is GuildUpdateOwnerEvent -> miniBus(event)
            is GuildUpdateRegionEvent -> miniBus(event)
            is GuildUpdateSplashEvent -> miniBus(event)
            is GuildUpdateVerificationLevelEvent -> miniBus(event)
            is GuildUpdateFeaturesEvent -> miniBus(event)
            is GuildMemberJoinEvent -> miniBus(event)
            is GuildMemberLeaveEvent -> miniBus(event)
            is GuildMemberRoleAddEvent -> miniBus(event)
            is GuildMemberRoleRemoveEvent -> miniBus(event)
            is GuildMemberNickChangeEvent -> miniBus(event)
            is GuildVoiceJoinEvent -> miniBus(event)
            is GuildVoiceMoveEvent -> miniBus(event)
            is GuildVoiceLeaveEvent -> miniBus(event)
            is GuildVoiceMuteEvent -> miniBus(event)
            is GuildVoiceDeafenEvent -> miniBus(event)
            is GuildVoiceGuildMuteEvent -> miniBus(event)
            is GuildVoiceGuildDeafenEvent -> miniBus(event)
            is GuildVoiceSelfMuteEvent -> miniBus(event)
            is GuildVoiceSelfDeafenEvent -> miniBus(event)
            is GuildVoiceSuppressEvent -> miniBus(event)
            is RoleCreateEvent -> miniBus(event)
            is RoleDeleteEvent -> miniBus(event)
            is RoleUpdateColorEvent -> miniBus(event)
            is RoleUpdateHoistedEvent -> miniBus(event)
            is RoleUpdateMentionableEvent -> miniBus(event)
            is RoleUpdateNameEvent -> miniBus(event)
            is RoleUpdatePermissionsEvent -> miniBus(event)
            is RoleUpdatePositionEvent -> miniBus(event)
            is EmoteAddedEvent -> miniBus(event)
            is EmoteRemovedEvent -> miniBus(event)
            is EmoteUpdateNameEvent -> miniBus(event)
            is EmoteUpdateRolesEvent -> miniBus(event)
            is HttpRequestEvent -> miniBus(event)
            is GuildVoiceUpdateEvent -> miniBus(event)
            is GenericMessageReactionEvent -> miniBus(event)
            is GenericPrivateMessageReactionEvent -> miniBus(event)
            is GenericTextChannelUpdateEvent<*> -> miniBus(event)
            is GenericCategoryUpdateEvent<*> -> miniBus(event)
            is GenericGuildMessageReactionEvent -> miniBus(event)
            is GenericVoiceChannelUpdateEvent<*> -> miniBus(event)
            is GenericGuildUpdateEvent<*> -> miniBus(event)
            is GenericGuildMemberEvent -> miniBus(event)
            is GenericGuildVoiceEvent -> miniBus(event)
            is GenericRoleUpdateEvent<*> -> miniBus(event)
            is GenericEmoteUpdateEvent<*> -> miniBus(event)
            //is GenericUserPresenceEvent -> miniBus(event)
            is GenericMessageEvent -> miniBus(event)
            is GenericPrivateMessageEvent -> miniBus(event)
            is GenericGuildMessageEvent -> miniBus(event)
            is GenericUserEvent -> miniBus(event)
            is GenericSelfUpdateEvent<*> -> miniBus(event)
            is GenericTextChannelEvent -> miniBus(event)
            is GenericVoiceChannelEvent -> miniBus(event)
            is GenericCategoryEvent -> miniBus(event)
            is GenericRoleEvent -> miniBus(event)
            is GenericEmoteEvent -> miniBus(event)
            is GenericGuildEvent -> miniBus(event)
            else -> miniBus(event)
        }
    }

}