
import 'dart:math';

import 'package:bloc/bloc.dart';
import 'package:thumbing/data/model/user/user.dart';
import 'package:thumbing/data/local/save_util.dart';
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
    if (event is InitializeLogin) {
      if (currentState is LoginInitial) {
        try {
          var userName = await SaveUtil.getSavedByKey("userName");
          var password = await SaveUtil.getSavedByKey("password");
          var token = await SaveUtil.getSavedByKey("token");
          if (userName != null && password != null) {
            yield currentState.copyWith(userName: userName, password: password);
            this.add(Login(token: token, userName: userName, password: password));
          }
        } catch (_) {}
      }
    }
    if (event is Login) {
      if (currentState is LoginInitial) {
        try {
          yield SubmissionInProgress();
          bool b = false;
          if(event.token != null){
            b = await userRepository.checkAuthorization(event.token, event.userName);
            this.add(Login(userName: event.userName, password: event.password));
          }
          else {
            final token =
            await userRepository.checkUser(event.userName, event.password);
            if (token == null) {
              yield LoginFailure(
                  message: "用户名或密码错误", times: currentState.times + 1, userName: event.userName, password: event.password);
            }
            else{
              b = true;
            }
          }
          if(b){
            User user = User(userName: event.userName, password: event.password);
            yield LoginSuccess(userInfo: user);
            await SaveUtil.saveByKey("userName", user.userName);
            await SaveUtil.saveByKey("password", user.password);
          }
        } catch (_) {
          if (event.token != null) {
            this.add(Login(userName: event.userName, password: event.password));
          }
          else {
            yield LoginFailure(
                userName: event.userName,
                password: event.password,
                message: "用户名或密码错误",
                times: currentState.times);
          }
        }
      }
      else if(currentState is SubmissionInProgress){
        try {
          final token =
          await userRepository.checkUser(event.userName, event.password);
          if (token == null) {
            yield LoginFailure(
                message: "用户名或密码错误",
                times: 1,
                userName: event.userName,
                password: event.password);
          }
          else {
            User user = User(
                userName: event.userName, password: event.password);
            yield LoginSuccess(userInfo: user);
            await SaveUtil.saveByKey("userName", user.userName);
            await SaveUtil.saveByKey("password", user.password);
          }
        }catch (_) {
          yield LoginFailure(
              userName: event.userName,
              password: event.password,
              message: "用户名或密码错误",
              times: 1);
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
