import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/user/user_rep.dart';
import 'package:thumbing/logic/event/user/login_event.dart';
import 'package:thumbing/logic/state/user/login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  UserRepository userRepository;
  LoginBloc() : super(LoginInitial(times: 0)) {
    userRepository = UserRepository();
  }
  @override
  Stream<LoginState> mapEventToState(LoginEvent event) async* {
    final currentState = state;
    if (event is Login) {
      if (currentState is LoginInitial) {
        try {
          yield SubmissionInProgress();
          final user =
              await userRepository.checkUser(event.userName, event.password);
          if (user == null)
            yield LoginFailure(
                message: "用户名或密码错误", times: currentState.times + 1);
          yield LoginSuccess(userInfo: user);
        } catch (_) {
          yield LoginFailure(
              userName: currentState.userName,
              password: currentState.password,
              message: "用户名或密码错误",
              times: currentState.times + 1);
        }
      }
    }
    if (event is Logout) {
      try {
        if (currentState is LoginSuccess) {
          await userRepository.logout(event.userName);
          yield LogoutSuccess();
        }
      } catch (_) {}
    }
    if (event is LoginUsernameChanged) {
      if (currentState is LoginFailure) {
        yield LoginInitial(
          times: currentState.times + 1,
          userName: event.userName,
          password: currentState.password,
        );
        return;
      }
      if (currentState is LogoutSuccess) {
        yield LoginInitial(times: 0, userName: event.userName);
        return;
      }
      if (currentState is LoginInitial) {
        yield currentState.copyWith(userName: event.userName);
      }
    }
    if (event is LoginPasswordChanged) {
      if (currentState is LoginFailure) {
        yield LoginInitial(
          times: currentState.times + 1,
          password: event.password,
          userName: currentState.userName,
        );
        return;
      }
      if (currentState is LogoutSuccess) {
        yield LoginInitial(times: 0, password: event.password);
        return;
      }
      if (currentState is LoginInitial) {
        yield currentState.copyWith(password: event.password);
      }
    }
  }
}
